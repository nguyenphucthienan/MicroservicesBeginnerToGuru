package com.nguyenphucthienan.msscbeerinventoryservice.service;

import com.nguyenphucthienan.brewery.model.BeerOrderDTO;

public interface AllocationService {

    Boolean allocateOrder(BeerOrderDTO beerOrderDTO);
}
