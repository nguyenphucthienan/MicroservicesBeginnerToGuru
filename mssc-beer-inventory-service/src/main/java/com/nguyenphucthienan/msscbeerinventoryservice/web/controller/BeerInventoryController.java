package com.nguyenphucthienan.msscbeerinventoryservice.web.controller;

import com.nguyenphucthienan.msscbeerinventoryservice.repository.BeerInventoryRepository;
import com.nguyenphucthienan.msscbeerinventoryservice.web.mapper.BeerInventoryMapper;
import com.nguyenphucthienan.msscbeerinventoryservice.web.model.BeerInventoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beers/{beerId}")
public class BeerInventoryController {

    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @GetMapping("/inventory")
    List<BeerInventoryDTO> listBeersById(@PathVariable UUID beerId) {
        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDTO)
                .collect(Collectors.toList());
    }
}
