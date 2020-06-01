package com.nguyenphucthienan.msscbeerservice.service;

import com.nguyenphucthienan.msscbeerservice.domain.Beer;
import com.nguyenphucthienan.msscbeerservice.repository.BeerRepository;
import com.nguyenphucthienan.msscbeerservice.web.controller.MvcNotFoundException;
import com.nguyenphucthienan.msscbeerservice.web.mapper.BeerMapper;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerPagedList;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList getBeers(String beerName, BeerStyleEnum beerStyleEnum, PageRequest pageRequest) {
        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyleEnum)) {
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyleEnum, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyleEnum)) {
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyleEnum)) {
            beerPage = beerRepository.findAllByBeerStyle(beerStyleEnum, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(beerPage
                .getContent()
                .stream()
                .map(beerMapper::beerToBeerDTO)
                .collect(Collectors.toList()),
                PageRequest
                        .of(beerPage.getPageable().getPageNumber(),
                                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

        return beerPagedList;
    }

    @Override
    public BeerDTO getBeerById(UUID id) {
        Beer beer = beerRepository.findById(id)
                .orElseThrow(() -> new MvcNotFoundException("Beer ID " + id + " not found"));
        return beerMapper.beerToBeerDTO(beer);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
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
