package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.msscbeerorderservice.bootstrap.BeerOrderBootstrap;
import com.nguyenphucthienan.msscbeerorderservice.domain.Customer;
import com.nguyenphucthienan.msscbeerorderservice.repository.BeerOrderRepository;
import com.nguyenphucthienan.msscbeerorderservice.repository.CustomerRepository;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderDTO;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderLineDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class TastingRoomService {

    private final CustomerRepository customerRepository;
    private final BeerOrderService beerOrderService;
    private final BeerOrderRepository beerOrderRepository;
    private final List<String> beerUpcs = new ArrayList<>(3);

    public TastingRoomService(CustomerRepository customerRepository, BeerOrderService beerOrderService,
                              BeerOrderRepository beerOrderRepository) {
        this.customerRepository = customerRepository;
        this.beerOrderService = beerOrderService;
        this.beerOrderRepository = beerOrderRepository;

        beerUpcs.add(BeerOrderBootstrap.BEER_1_UPC);
        beerUpcs.add(BeerOrderBootstrap.BEER_2_UPC);
        beerUpcs.add(BeerOrderBootstrap.BEER_3_UPC);
    }

    @Transactional
    @Scheduled(fixedRate = 2000) // Run every 2 seconds
    public void placeTastingRoomOrder() {
        List<Customer> customers = customerRepository.findAllByCustomerNameLike(BeerOrderBootstrap.TASTING_ROOM);
        if (customers.size() == 1) { // Should be just one
            doPlaceOrder(customers.get(0));
        } else {
            log.error("Too many or too few tasting room customers found");
        }
    }

    private void doPlaceOrder(Customer customer) {
        String beerToOrder = getRandomBeerUpc();

        BeerOrderLineDTO beerOrderLineDTO = BeerOrderLineDTO.builder()
                .upc(beerToOrder)
                .orderQuantity(new Random().nextInt(6)) // TODO: externalize value to property
                .build();

        List<BeerOrderLineDTO> beerOrderLineDTOs = new ArrayList<>();
        beerOrderLineDTOs.add(beerOrderLineDTO);

        BeerOrderDTO beerOrderDTO = BeerOrderDTO.builder()
                .customerId(customer.getId())
                .customerRef(UUID.randomUUID().toString())
                .beerOrderLines(beerOrderLineDTOs)
                .build();

        BeerOrderDTO savedBeerOrderDTO = beerOrderService.placeOrder(customer.getId(), beerOrderDTO);
    }

    private String getRandomBeerUpc() {
        return beerUpcs.get(new Random().nextInt(beerUpcs.size()));
    }
}
