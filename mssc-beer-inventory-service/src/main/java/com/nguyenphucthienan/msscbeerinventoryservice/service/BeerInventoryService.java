package com.nguyenphucthienan.msscbeerinventoryservice.service;

import com.nguyenphucthienan.msscbeerinventoryservice.web.model.BeerInventoryDTO;

import java.util.List;
import java.util.UUID;

public interface BeerInventoryService {

    List<BeerInventoryDTO> getBeerInventoriesByBeerId(UUID beerId);
}
