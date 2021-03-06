package com.nguyenphucthienan.msscbeerservice.service.inventory;

import com.nguyenphucthienan.brewery.model.BeerInventoryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("!local-discovery")
@Slf4j
@ConfigurationProperties(prefix = "com.npta", ignoreUnknownFields = false)
@Component
public class BeerInventoryServiceRestTemplateImpl implements BeerInventoryService {

    public static final String INVENTORY_PATH = "/api/v1/beers/{beerId}/inventory";

    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public BeerInventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {
        ResponseEntity<List<BeerInventoryDTO>> responseEntity = restTemplate
                .exchange(beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        }, beerId);

        return Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BeerInventoryDTO::getQuantityOnHand)
                .sum();
    }
}
