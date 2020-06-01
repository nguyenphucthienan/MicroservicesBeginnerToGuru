package com.nguyenphucthienan.msscbeerorderservice.web.mapper;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderLine;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderLineDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {

    BeerOrderLineDTO beerOrderLineToDTO(BeerOrderLine beerOrderLine);

    BeerOrderLine beerDTOToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO);
}
