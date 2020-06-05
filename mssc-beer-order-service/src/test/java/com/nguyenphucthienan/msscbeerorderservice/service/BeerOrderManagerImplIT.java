package com.nguyenphucthienan.msscbeerorderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jenspiegsa.wiremockextension.WireMockExtension;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nguyenphucthienan.brewery.model.BeerDTO;
import com.nguyenphucthienan.brewery.model.event.AllocationFailureEvent;
import com.nguyenphucthienan.msscbeerorderservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderLine;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.nguyenphucthienan.msscbeerorderservice.domain.Customer;
import com.nguyenphucthienan.msscbeerorderservice.repository.BeerOrderRepository;
import com.nguyenphucthienan.msscbeerorderservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(WireMockExtension.class)
public class BeerOrderManagerImplIT {

    @Autowired
    private BeerOrderManager beerOrderManager;

    @Autowired
    private BeerOrderRepository beerOrderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private WireMockServer wireMockServer;

    private Customer customer;
    private final UUID beerId = UUID.randomUUID();
    private final String beerUPC = "123456789";

    @TestConfiguration
    static class RestTemplateBuilderProvider {
        @Bean(destroyMethod = "stop")
        public WireMockServer wireMockServer() {
            WireMockServer server = with(wireMockConfig().port(8083));
            server.start();
            return server;
        }
    }

    @BeforeEach
    public void setUp() {
        customer = customerRepository.save(Customer.builder()
                .customerName("Test Customer")
                .build());
    }

    @Test
    void testNewToAllocated() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder().id(beerId).upc(beerUPC).build();

        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + beerUPC)
                .willReturn(okJson(objectMapper.writeValueAsString(beerDTO))));

        BeerOrder beerOrder = createBeerOrder();
        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                assertEquals(BeerOrderStatusEnum.ALLOCATED, foundBeerOrder.getOrderStatus());
            });
        });

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                BeerOrderLine line = foundBeerOrder.getBeerOrderLines().iterator().next();
                assertEquals(line.getOrderQuantity(), line.getQuantityAllocated());
            });
        });

        beerOrderRepository.findById(savedBeerOrder.getId()).ifPresent(savedBeerOrder2 -> {
            assertNotNull(savedBeerOrder);
            assertEquals(BeerOrderStatusEnum.ALLOCATED, savedBeerOrder2.getOrderStatus());
        });
    }

    @Test
    void testFailedValidation() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder().id(beerId).upc(beerUPC).build();

        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + beerUPC)
                .willReturn(okJson(objectMapper.writeValueAsString(beerDTO))));

        BeerOrder beerOrder = createBeerOrder();
        beerOrder.setCustomerRef("fail-validation");

        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                assertEquals(BeerOrderStatusEnum.VALIDATION_EXCEPTION, foundBeerOrder.getOrderStatus());
            });
        });
    }

    @Test
    void testFailedAllocation() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder().id(beerId).upc(beerUPC).build();

        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + beerUPC)
                .willReturn(okJson(objectMapper.writeValueAsString(beerDTO))));

        BeerOrder beerOrder = createBeerOrder();
        beerOrder.setCustomerRef("fail-allocation");

        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                assertEquals(BeerOrderStatusEnum.ALLOCATION_EXCEPTION, foundBeerOrder.getOrderStatus());
            });
        });

        AllocationFailureEvent allocationFailureEvent = (AllocationFailureEvent) jmsTemplate.receiveAndConvert(JmsConfig.ALLOCATE_FAILURE_QUEUE);

        assertNotNull(allocationFailureEvent);
        assertThat(allocationFailureEvent.getOrderId()).isEqualTo(savedBeerOrder.getId());
    }

    @Test
    void testPartialAllocation() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder().id(beerId).upc(beerUPC).build();

        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + beerUPC)
                .willReturn(okJson(objectMapper.writeValueAsString(beerDTO))));

        BeerOrder beerOrder = createBeerOrder();
        beerOrder.setCustomerRef("partial-allocation");

        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                assertEquals(BeerOrderStatusEnum.PENDING_INVENTORY, foundBeerOrder.getOrderStatus());
            });
        });
    }

    @Test
    void testNewToPickedUp() throws JsonProcessingException {
        BeerDTO beerDTO = BeerDTO.builder().id(beerId).upc(beerUPC).build();

        wireMockServer.stubFor(get(BeerServiceImpl.BEER_UPC_PATH_V1 + beerUPC)
                .willReturn(okJson(objectMapper.writeValueAsString(beerDTO))));

        BeerOrder beerOrder = createBeerOrder();
        BeerOrder savedBeerOrder = beerOrderManager.newBeerOrder(beerOrder);

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                assertEquals(BeerOrderStatusEnum.ALLOCATED, foundBeerOrder.getOrderStatus());
            });
        });

        beerOrderManager.beerOrderPickedUp(savedBeerOrder.getId());

        await().untilAsserted(() -> {
            beerOrderRepository.findById(beerOrder.getId()).ifPresent(foundBeerOrder -> {
                assertEquals(BeerOrderStatusEnum.PICKED_UP, foundBeerOrder.getOrderStatus());
            });
        });

        beerOrderRepository.findById(savedBeerOrder.getId()).ifPresent(pickedUpBeerOrder -> {
            assertEquals(BeerOrderStatusEnum.PICKED_UP, pickedUpBeerOrder.getOrderStatus());
        });
    }

    private BeerOrder createBeerOrder() {
        BeerOrder beerOrder = BeerOrder.builder()
                .customer(customer)
                .build();

        Set<BeerOrderLine> lines = new HashSet<>();
        lines.add(BeerOrderLine.builder()
                .beerId(beerId)
                .upc(beerUPC)
                .orderQuantity(1)
                .beerOrder(beerOrder)
                .build());

        beerOrder.setBeerOrderLines(lines);
        return beerOrder;
    }
}
