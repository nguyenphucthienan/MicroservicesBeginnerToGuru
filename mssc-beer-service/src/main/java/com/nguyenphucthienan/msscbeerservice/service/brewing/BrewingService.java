package com.nguyenphucthienan.msscbeerservice.service.brewing;

import com.nguyenphucthienan.msscbeerservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerservice.domain.Beer;
import com.nguyenphucthienan.brewery.model.event.BrewBeerEvent;
import com.nguyenphucthienan.msscbeerservice.repository.BeerRepository;
import com.nguyenphucthienan.msscbeerservice.service.inventory.BeerInventoryService;
import com.nguyenphucthienan.msscbeerservice.web.mapper.BeerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000) // Every 5 seconds
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();
        beers.forEach(beer -> {
            Integer inventoryQuantityOnHand = beerInventoryService.getOnHandInventory(beer.getId());

            log.debug("Min On Hand: " + beer.getMinOnHand());
            log.debug("Inventory: " + inventoryQuantityOnHand);

            if (beer.getMinOnHand() >= inventoryQuantityOnHand) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE,
                        new BrewBeerEvent(beerMapper.beerToBeerDTO(beer)));
            }
        });
    }
}
