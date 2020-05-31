package com.nguyenphucthienan.msscbeerservice.service;

import com.nguyenphucthienan.msscbeerservice.domain.Beer;
import com.nguyenphucthienan.msscbeerservice.repository.BeerRepository;
import com.nguyenphucthienan.msscbeerservice.web.controller.MvcNotFoundException;
import com.nguyenphucthienan.msscbeerservice.web.mapper.BeerMapper;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDTO getBeerById(UUID id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new MvcNotFoundException("Beer ID " + id + " not found"));
        return beerMapper.beerToBeerDTO(beer);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOTOBeer(beerDTO)));
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new MvcNotFoundException("Beer ID " + id + " not found"));

        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle().name());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());
        return beerMapper.beerToBeerDTO(beerRepository.save(beer));
    }
}
