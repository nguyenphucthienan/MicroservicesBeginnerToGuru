package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.brewery.model.BeerOrderDTO;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);

    void beerOrderAllocationPassed(BeerOrderDTO beerOrderDTO);

    void beerOrderAllocationPendingInventory(BeerOrderDTO beerOrderDTO);

    void beerOrderAllocationFailed(BeerOrderDTO beerOrderDTO);

    void beerOrderPickedUp(UUID beerOrderId);
}
