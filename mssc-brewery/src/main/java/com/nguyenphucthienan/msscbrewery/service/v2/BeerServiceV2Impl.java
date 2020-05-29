package com.nguyenphucthienan.msscbrewery.service.v2;

import com.nguyenphucthienan.msscbrewery.web.model.v2.BeerDTOV2;
import com.nguyenphucthienan.msscbrewery.web.model.v2.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceV2Impl implements BeerServiceV2 {

    @Override
    public BeerDTOV2 getBeerById(UUID id) {
        return BeerDTOV2.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(123456789L)
                .build();
    }

    @Override
    public BeerDTOV2 saveBeer(BeerDTOV2 beerDTO) {
        return BeerDTOV2.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(123456789L)
                .build();
    }

    @Override
    public BeerDTOV2 updateBeer(UUID id, BeerDTOV2 beerDTO) {
        log.info("Update Beer ID: {}", id);
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        log.info("Delete Beer ID: {}", id);
    }
}
