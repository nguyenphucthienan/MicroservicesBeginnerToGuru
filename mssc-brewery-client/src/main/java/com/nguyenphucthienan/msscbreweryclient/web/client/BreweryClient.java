package com.nguyenphucthienan.msscbreweryclient.web.client;

import com.nguyenphucthienan.msscbreweryclient.web.model.BeerDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "com.nguyenphucthienan", ignoreUnknownFields = false)
public class BreweryClient {

    private final String BEER_API_V1 = "/api/v1/beers";
    private final RestTemplate restTemplate;

    private String apiHost;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BeerDTO getBeer(UUID uuid) {
        return restTemplate.getForObject(apiHost + BEER_API_V1 + "/" + uuid.toString(), BeerDTO.class);
    }

    public URI saveBeer(BeerDTO beerDTO) {
        return restTemplate.postForLocation(apiHost + BEER_API_V1, beerDTO);
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }
}
