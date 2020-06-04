package com.nguyenphucthienan.msscbeerinventoryservice.web.mapper;

import com.nguyenphucthienan.msscbeerinventoryservice.domain.BeerInventory;
import com.nguyenphucthienan.brewery.model.BeerInventoryDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerInventoryMapper {

    BeerInventoryDTO beerInventoryToBeerInventoryDTO(BeerInventory beerInventory);

    BeerInventory beerInventoryDTOToBeerInventory(BeerInventoryDTO beerInventoryDTO);
}
