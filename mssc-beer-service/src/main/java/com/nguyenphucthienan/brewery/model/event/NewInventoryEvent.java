package com.nguyenphucthienan.brewery.model.event;

import com.nguyenphucthienan.brewery.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
