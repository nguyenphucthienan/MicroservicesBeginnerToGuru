package com.nguyenphucthienan.msscbreweryclient.web.client;

import com.nguyenphucthienan.msscbreweryclient.web.model.BeerDTO;
import com.nguyenphucthienan.msscbreweryclient.web.model.CustomerDTO;
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
    private final String CUSTOMER_API_V1 = "/api/v1/customers";

    private final RestTemplate restTemplate;
    private String apiHost;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public BeerDTO getBeer(UUID uuid) {
        return restTemplate.getForObject(apiHost + BEER_API_V1 + "/" + uuid, BeerDTO.class);
    }

    public URI saveBeer(BeerDTO beerDTO) {
        return restTemplate.postForLocation(apiHost + BEER_API_V1, beerDTO);
    }

    public void updateBeer(UUID uuid, BeerDTO beerDTO) {
        restTemplate.put(apiHost + BEER_API_V1 + "/" + uuid.toString(), beerDTO);
    }

    public void deleteBeer(UUID uuid) {
        restTemplate.delete(apiHost + BEER_API_V1 + "/" + uuid);
    }

    public CustomerDTO getCustomer(UUID uuid) {
        return restTemplate.getForObject(apiHost + CUSTOMER_API_V1 + "/" + uuid, CustomerDTO.class);
    }

    public URI saveCustomer(CustomerDTO customerDTO) {
        return restTemplate.postForLocation(apiHost + CUSTOMER_API_V1, customerDTO);
    }

    public void updateCustomer(UUID uuid, CustomerDTO customerDTO) {
        restTemplate.put(apiHost + CUSTOMER_API_V1 + "/" + uuid, customerDTO);
    }

    public void deleteCustomer(UUID uuid) {
        restTemplate.delete(apiHost + CUSTOMER_API_V1 + "/" + uuid);
    }
}
