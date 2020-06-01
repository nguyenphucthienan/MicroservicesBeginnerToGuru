package com.nguyenphucthienan.msscbeerorderservice.web.mapper;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    BeerOrderDTO beerOrderToBeerDTO(BeerOrder beerOrder);

    BeerOrder beerDTOToBeerOrder(BeerOrderDTO beerOrderDTO);
}
