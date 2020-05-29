package com.nguyenphucthienan.msscbrewery.service;

import com.nguyenphucthienan.msscbrewery.web.model.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
