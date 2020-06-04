package com.nguyenphucthienan.msscbeerorderservice.web.mapper;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderLine;
import com.nguyenphucthienan.msscbeerorderservice.service.BeerService;
import com.nguyenphucthienan.brewery.model.BeerDTO;
import com.nguyenphucthienan.brewery.model.BeerOrderLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public abstract class BeerOrderLineMapperDecorator implements BeerOrderLineMapper {

    private BeerService beerService;
    private BeerOrderLineMapper beerOrderLineMapper;

    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }

    @Override
    public BeerOrderLineDTO beerOrderLineToBeerOrderLineDTO(BeerOrderLine beerOrderLine) {
        BeerOrderLineDTO beerOrderLineDTO = beerOrderLineMapper.beerOrderLineToBeerOrderLineDTO(beerOrderLine);
        Optional<BeerDTO> beerDtoOptional = beerService.getBeerByUpc(beerOrderLine.getUpc());

        beerDtoOptional.ifPresent(beerDto -> {
            beerOrderLineDTO.setBeerId(beerDto.getId());
            beerOrderLineDTO.setBeerName(beerDto.getBeerName());
            beerOrderLineDTO.setBeerStyle(beerDto.getBeerStyle());
            beerOrderLineDTO.setPrice(beerDto.getPrice());
        });

        return beerOrderLineDTO;
    }
}
