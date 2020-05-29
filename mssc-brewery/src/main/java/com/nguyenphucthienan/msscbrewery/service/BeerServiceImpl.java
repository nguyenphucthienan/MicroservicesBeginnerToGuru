package com.nguyenphucthienan.msscbrewery.service;

import com.nguyenphucthienan.msscbrewery.web.model.BeerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDTO getBeerById(UUID id) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .upc(123456789L)
                .build();
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .upc(beerDTO.getUpc())
                .build();
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
        log.info("Update Beer ID: {}", id);
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete Beer ID: {}", id);
    }
}
