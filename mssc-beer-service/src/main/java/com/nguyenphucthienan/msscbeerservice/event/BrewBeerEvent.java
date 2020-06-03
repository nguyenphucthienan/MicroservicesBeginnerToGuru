package com.nguyenphucthienan.msscbeerservice.event;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;

public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
