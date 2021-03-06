package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.brewery.model.BeerDTO;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    Optional<BeerDTO> getBeerById(UUID id);

    Optional<BeerDTO> getBeerByUpc(String upc);
}
