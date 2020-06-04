package com.nguyenphucthienan.msscbeerinventoryservice.service;

import com.nguyenphucthienan.brewery.model.BeerInventoryDTO;

import java.util.List;
import java.util.UUID;

public interface BeerInventoryService {

    List<BeerInventoryDTO> getBeerInventoriesByBeerId(UUID beerId);
}
