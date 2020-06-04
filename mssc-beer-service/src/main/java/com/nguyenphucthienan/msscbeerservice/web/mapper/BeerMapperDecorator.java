package com.nguyenphucthienan.msscbeerservice.web.mapper;

import com.nguyenphucthienan.msscbeerservice.domain.Beer;
import com.nguyenphucthienan.msscbeerservice.service.inventory.BeerInventoryService;
import com.nguyenphucthienan.brewery.model.BeerDTO;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO beerToBeerDTO(Beer beer) {
        return mapper.beerToBeerDTO(beer);
    }

    @Override
    public BeerDTO beerToBeerDTOWithInventory(Beer beer) {
        BeerDTO beerDTO = mapper.beerToBeerDTO(beer);
        beerDTO.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
        return beerDTO;
    }

    @Override
    public Beer beerDTOToBeer(BeerDTO beerDTO) {
        return mapper.beerDTOToBeer(beerDTO);
    }
}
