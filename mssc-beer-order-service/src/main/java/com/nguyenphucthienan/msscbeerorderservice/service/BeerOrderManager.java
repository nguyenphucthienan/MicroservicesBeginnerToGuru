package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID beerOrderId, Boolean isValid);
}
