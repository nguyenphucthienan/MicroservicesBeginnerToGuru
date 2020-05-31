package com.nguyenphucthienan.msscbeerorderservice.web.mapper;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDTO beerOrderToBeerDTO(BeerOrder beerOrder);

    BeerOrder beerDTOToBeerOrder(BeerOrderDTO beerOrderDTO);
}
