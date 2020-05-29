package com.nguyenphucthienan.msscbrewery.service;

import com.nguyenphucthienan.msscbrewery.web.model.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO getBeerById(UUID id);
}
