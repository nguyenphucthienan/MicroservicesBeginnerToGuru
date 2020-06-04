package com.nguyenphucthienan.brewery.model.event;

import com.nguyenphucthienan.brewery.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
