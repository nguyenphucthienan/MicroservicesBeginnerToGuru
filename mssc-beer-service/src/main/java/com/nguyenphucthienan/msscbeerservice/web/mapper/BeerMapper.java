package com.nguyenphucthienan.msscbeerservice.web.mapper;

import com.nguyenphucthienan.msscbeerservice.domain.Beer;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);

    BeerDTO beerToBeerDTOWithInventory(Beer beer);

    Beer beerDTOToBeer(BeerDTO beerDTO);
}
