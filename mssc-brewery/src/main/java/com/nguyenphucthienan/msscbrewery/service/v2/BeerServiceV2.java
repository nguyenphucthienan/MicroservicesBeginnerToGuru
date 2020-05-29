package com.nguyenphucthienan.msscbrewery.service.v2;

import com.nguyenphucthienan.msscbrewery.web.model.v2.BeerDTOV2;

import java.util.UUID;

public interface BeerServiceV2 {

    BeerDTOV2 getBeerById(UUID id);

    BeerDTOV2 saveBeer(BeerDTOV2 beerDTO);

    BeerDTOV2 updateBeer(UUID id, BeerDTOV2 beerDTO);

    void deleteById(UUID id);
}
