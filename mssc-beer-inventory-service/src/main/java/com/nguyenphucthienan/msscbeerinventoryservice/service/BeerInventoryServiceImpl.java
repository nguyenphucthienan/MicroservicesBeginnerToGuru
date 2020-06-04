package com.nguyenphucthienan.msscbeerinventoryservice.service;

import com.nguyenphucthienan.msscbeerinventoryservice.repository.BeerInventoryRepository;
import com.nguyenphucthienan.msscbeerinventoryservice.web.mapper.BeerInventoryMapper;
import com.nguyenphucthienan.brewery.model.BeerInventoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerInventoryServiceImpl implements BeerInventoryService {

    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @Override
    public List<BeerInventoryDTO> getBeerInventoriesByBeerId(UUID beerId) {
        return beerInventoryRepository.findAllByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDTO)
                .collect(Collectors.toList());
    }
}
