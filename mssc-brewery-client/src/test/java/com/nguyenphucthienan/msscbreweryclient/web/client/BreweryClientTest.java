package com.nguyenphucthienan.msscbreweryclient.web.client;

import com.nguyenphucthienan.msscbreweryclient.web.model.BeerDTO;
import com.nguyenphucthienan.msscbreweryclient.web.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BreweryClientTest {

    @Autowired
    private BreweryClient breweryClient;

    @Test
    public void getBeer() {
        BeerDTO beerDTO = breweryClient.getBeer(UUID.randomUUID());
        assertNotNull(beerDTO);
    }

    @Test
    public void saveBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("Beer Name").build();
        URI uri = breweryClient.saveBeer(beerDTO);
        assertNotNull(uri);
    }

    @Test
    public void updateBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("Beer Name").build();
        breweryClient.updateBeer(UUID.randomUUID(), beerDTO);
    }

    @Test
    public void deleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }

    @Test
    public void getCustomer() {
        CustomerDTO customerDTO = breweryClient.getCustomer(UUID.randomUUID());
        assertNotNull(customerDTO);
    }

    @Test
    public void saveCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Customer").build();
        URI uri = breweryClient.saveCustomer(customerDTO);
        assertNotNull(uri);
    }

    @Test
    public void updateCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("Customer").build();
        breweryClient.updateCustomer(UUID.randomUUID(), customerDTO);
    }

    @Test
    public void deleteCustomer() {
        breweryClient.deleteCustomer(UUID.randomUUID());
    }
}
