package com.nguyenphucthienan.msscbeerorderservice.web.mapper;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderLine;
import com.nguyenphucthienan.brewery.model.BeerOrderLineDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerOrderLineMapperDecorator.class)
public interface BeerOrderLineMapper {

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDTO(BeerOrderLine beerOrderLine);

    BeerOrderLine beerDTOToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO);
}
