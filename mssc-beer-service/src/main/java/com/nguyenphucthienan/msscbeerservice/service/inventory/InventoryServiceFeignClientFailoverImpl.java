package com.nguyenphucthienan.msscbeerservice.service.inventory;

import com.nguyenphucthienan.brewery.model.BeerInventoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailoverImpl implements InventoryServiceFeignClient {

    private final InventoryFailoverServiceFeignClient inventoryFailoverServiceFeignClient;

    @Override
    public ResponseEntity<List<BeerInventoryDTO>> getOnHandInventories(UUID beerId) {
        return inventoryFailoverServiceFeignClient.getOnHandInventories();
    }
}
