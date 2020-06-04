package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.nguyenphucthienan.msscbeerservice.service.BeerService;
import com.nguyenphucthienan.brewery.model.BeerDTO;
import com.nguyenphucthienan.brewery.model.BeerPagedList;
import com.nguyenphucthienan.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping("/beers")
    public ResponseEntity<BeerPagedList> getBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                  @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                  @RequestParam(value = "beerName", required = false) String beerName,
                                                  @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyleEnum,
                                                  @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        BeerPagedList beerList = beerService.getBeers(beerName, beerStyleEnum,
                PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/beers/{id}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID id,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(beerService.getBeerById(id, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDTO> getBeerByUpc(@PathVariable String upc) {
        return new ResponseEntity<>(beerService.getBeerByUpc(upc), HttpStatus.OK);
    }

    @PostMapping("/beers")
    public ResponseEntity<BeerDTO> saveBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.saveBeer(beerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/beers/{id}")
    public ResponseEntity<BeerDTO> updateBeer(@Valid @PathVariable UUID id, @Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(beerService.updateBeer(id, beerDTO), HttpStatus.OK);
    }
}
