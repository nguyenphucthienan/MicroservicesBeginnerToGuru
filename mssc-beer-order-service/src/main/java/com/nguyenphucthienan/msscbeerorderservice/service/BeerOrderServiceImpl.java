package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.domain.Customer;
import com.nguyenphucthienan.msscbeerorderservice.domain.OrderStatusEnum;
import com.nguyenphucthienan.msscbeerorderservice.repository.BeerOrderRepository;
import com.nguyenphucthienan.msscbeerorderservice.repository.CustomerRepository;
import com.nguyenphucthienan.msscbeerorderservice.web.mapper.BeerOrderMapper;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderDTO;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final ApplicationEventPublisher publisher;

    public BeerOrderServiceImpl(BeerOrderRepository beerOrderRepository, CustomerRepository customerRepository,
                                BeerOrderMapper beerOrderMapper, ApplicationEventPublisher publisher) {
        this.beerOrderRepository = beerOrderRepository;
        this.customerRepository = customerRepository;
        this.beerOrderMapper = beerOrderMapper;
        this.publisher = publisher;
    }

    @Override
    public BeerOrderPagedList getOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            return null;
        }

        Page<BeerOrder> beerOrderPage = beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);
        return new BeerOrderPagedList(beerOrderPage
                .stream()
                .map(beerOrderMapper::beerOrderToBeerDTO)
                .collect(Collectors.toList()), PageRequest.of(
                beerOrderPage.getPageable().getPageNumber(),
                beerOrderPage.getPageable().getPageSize()),
                beerOrderPage.getTotalElements());
    }

    @Override
    public BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDTO) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            // TODO: Add exception type
            throw new RuntimeException("Customer not found");
        }

        BeerOrder beerOrder = beerOrderMapper.beerDTOToBeerOrder(beerOrderDTO);
        beerOrder.setId(null); // Should not be set by outside client
        beerOrder.setCustomer(customerOptional.get());
        beerOrder.setOrderStatus(OrderStatusEnum.NEW);
        beerOrder.getBeerOrderLines().forEach(line -> line.setBeerOrder(beerOrder));
        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

        log.debug("Saved BeerOrder ID: " + beerOrder.getId());

        // TODO: Implement
        // publisher.publishEvent(new NewBeerOrderEvent(savedBeerOrder));
        return beerOrderMapper.beerOrderToBeerDTO(savedBeerOrder);
    }

    @Override
    public BeerOrderDTO getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToBeerDTO(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);
        beerOrderRepository.save(beerOrder);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }

        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);
        if (beerOrderOptional.isEmpty()) {
            throw new RuntimeException("BeerOrder not found");
        }

        BeerOrder beerOrder = beerOrderOptional.get();

        // Fall to exception if customer IDs do not match - order not for customer
        if (!beerOrder.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException("BeerOrder not found");
        }

        return beerOrder;
    }
}
