package com.nguyenphucthienan.msscbrewery.web.controller.v2;

import com.nguyenphucthienan.msscbrewery.service.v2.BeerServiceV2;
import com.nguyenphucthienan.msscbrewery.web.model.v2.BeerDTOV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v2/beers")
public class BeerControllerV2 {

    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTOV2> getBeer(@PathVariable UUID id) {
        return new ResponseEntity<>(beerService.getBeerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createBeer(@RequestBody BeerDTOV2 beerDTO) {
        BeerDTOV2 savedBeerDTO = beerService.saveBeer(beerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beers/" + savedBeerDTO.getId().toString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBeer(@PathVariable UUID id, @RequestBody BeerDTOV2 beerDTO) {
        return new ResponseEntity<>(beerService.updateBeer(id, beerDTO), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("id") UUID id) {
        beerService.deleteById(id);
    }
}
