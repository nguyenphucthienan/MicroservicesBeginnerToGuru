package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.brewery.model.BeerOrderDTO;
import com.nguyenphucthienan.brewery.model.BeerOrderPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerOrderService {

    BeerOrderPagedList getOrders(UUID customerId, Pageable pageable);

    BeerOrderDTO placeOrder(UUID customerId, BeerOrderDTO beerOrderDTO);

    BeerOrderDTO getOrderById(UUID customerId, UUID orderId);

    void pickupOrder(UUID customerId, UUID orderId);
}
