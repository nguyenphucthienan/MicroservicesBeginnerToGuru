package com.nguyenphucthienan.msscbrewery.web.controller;

import com.nguyenphucthienan.msscbrewery.service.BeerService;
import com.nguyenphucthienan.msscbrewery.web.model.BeerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable UUID id) {
        return new ResponseEntity<>(beerService.getBeerById(id), HttpStatus.OK);
    }

}
