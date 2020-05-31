package com.nguyenphucthienan.msscjacksonexample.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {

    protected BeerDTO getValidBeerDTO() {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Beer Name")
                .beerStyle("Ale")
                .price(new BigDecimal("2.99"))
                .upc(123456789L)
                .createdDate(OffsetDateTime.now())
                .lastUpdatedDate(OffsetDateTime.now())
                .localDate(LocalDate.now())
                .build();
    }
}
