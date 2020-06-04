package com.nguyenphucthienan.msscbeerinventoryservice.service;

import com.nguyenphucthienan.brewery.model.BeerDTO;
import com.nguyenphucthienan.brewery.model.event.NewInventoryEvent;
import com.nguyenphucthienan.msscbeerinventoryservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerinventoryservice.domain.BeerInventory;
import com.nguyenphucthienan.msscbeerinventoryservice.repository.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewInventoryListener {

    private final BeerInventoryRepository beerInventoryRepository;

    @Transactional
    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void listen(NewInventoryEvent event) {
        log.debug("Got Inventory: " + event.toString());

        BeerDTO beerDTO = event.getBeerDTO();
        beerInventoryRepository.save(BeerInventory.builder()
                .beerId(beerDTO.getId())
                .upc(beerDTO.getUpc())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .build());
    }
}
