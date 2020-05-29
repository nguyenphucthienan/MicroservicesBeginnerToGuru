package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID id) {
        // TODO: Implement
        return new ResponseEntity<>(BeerDTO.builder().build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveBeer(@RequestBody BeerDTO beerDTO) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeer(@PathVariable UUID id, @RequestBody BeerDTO beerDTO) {
        // TODO: Implement
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
