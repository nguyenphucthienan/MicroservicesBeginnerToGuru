package com.nguyenphucthienan.msscbeerservice.service;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerPagedList;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList getBeers(String beerName, BeerStyleEnum beerStyleEnum, PageRequest pageRequest);

    BeerDTO getBeerById(UUID id);

    BeerDTO saveBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);
}
