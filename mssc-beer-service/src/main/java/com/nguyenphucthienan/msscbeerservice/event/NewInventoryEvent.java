package com.nguyenphucthienan.msscbeerservice.event;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;

public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
