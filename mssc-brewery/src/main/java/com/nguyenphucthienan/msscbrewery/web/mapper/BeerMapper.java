package com.nguyenphucthienan.msscbrewery.web.mapper;

import com.nguyenphucthienan.msscbrewery.domain.Beer;
import com.nguyenphucthienan.msscbrewery.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {

    BeerDTO beerToBeerDTO(Beer beer);

    Beer beerDTOTOBeer(BeerDTO beerDTO);
}
