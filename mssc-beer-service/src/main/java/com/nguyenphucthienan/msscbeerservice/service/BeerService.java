package com.nguyenphucthienan.msscbeerservice.service;

import com.nguyenphucthienan.brewery.model.BeerDTO;
import com.nguyenphucthienan.brewery.model.BeerPagedList;
import com.nguyenphucthienan.brewery.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList getBeers(String beerName, BeerStyleEnum beerStyleEnum,
                           PageRequest pageRequest, Boolean showInventoryOnHand);

    BeerDTO getBeerById(UUID id, Boolean showInventoryOnHand);

    BeerDTO getBeerByUpc(String upc);

    BeerDTO saveBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);
}
