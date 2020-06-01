package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.nguyenphucthienan.msscbeerservice.service.BeerService;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerPagedList;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<BeerPagedList> getBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "beerName", required = false) String beerName,
                                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyleEnum) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.getBeers(beerName, beerStyleEnum, PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

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
