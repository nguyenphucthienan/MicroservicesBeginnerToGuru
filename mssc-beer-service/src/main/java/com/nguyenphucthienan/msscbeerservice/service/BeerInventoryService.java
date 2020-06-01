package com.nguyenphucthienan.msscbeerservice.service;

import java.util.UUID;

public interface BeerInventoryService {

    Integer getOnHandInventory(UUID beerId);
}
