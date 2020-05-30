package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(BeerController.BASE_URL)
public class BeerController {

    public static final String BASE_URL = "/api/v1/beers";

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeer(@PathVariable UUID id) {
        // TODO: Implement
        return new ResponseEntity<>(BeerDTO.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveBeer(@Valid @RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeer(@Valid @PathVariable UUID id, @Valid @RequestBody BeerDTO beerDTO) {
        // TODO: Implement
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
