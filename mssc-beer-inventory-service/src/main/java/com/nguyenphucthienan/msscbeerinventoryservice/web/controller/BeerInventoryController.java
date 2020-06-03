package com.nguyenphucthienan.msscbeerinventoryservice.web.controller;

import com.nguyenphucthienan.msscbeerinventoryservice.service.BeerInventoryService;
import com.nguyenphucthienan.msscbeerinventoryservice.web.model.BeerInventoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beers/{beerId}")
public class BeerInventoryController {

    private final BeerInventoryService beerInventoryService;

    @GetMapping("/inventory")
    List<BeerInventoryDTO> getBeerInventoriesByBeerId(@PathVariable UUID beerId) {
        return beerInventoryService.getBeerInventoriesByBeerId(beerId);
    }
}
