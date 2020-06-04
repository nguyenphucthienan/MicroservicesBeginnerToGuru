package com.nguyenphucthienan.msscbeerservice.service.order;

import com.nguyenphucthienan.brewery.model.BeerOrderDTO;
import com.nguyenphucthienan.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDTO beerOrderDTO) {
        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrderDTO.getBeerOrderLines().forEach(orderLineDTO -> {
            if (beerRepository.findByUpc(orderLineDTO.getUpc()).isEmpty()) {
                beersNotFound.incrementAndGet();
            }
        });

        return beersNotFound.get() == 0;
    }
}
