package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.nguyenphucthienan.msscbeerservice.service.BeerService;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(BeerController.BASE_URL)
public class BeerController {

    public static final String BASE_URL = "/api/v1/beers";

    private final BeerService beerService;

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable UUID id) {
        return new ResponseEntity<>(beerService.getBeerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDTO> saveBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.saveBeer(beerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeerDTO> updateBeer(@Valid @PathVariable UUID id, @Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.updateBeer(id, beerDTO), HttpStatus.OK);
    }
}
