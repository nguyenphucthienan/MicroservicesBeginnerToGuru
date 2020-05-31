package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyenphucthienan.msscbeerservice.service.BeerService;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import com.nguyenphucthienan.msscbeerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
@EnableJpaRepositories
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    public void getBeer() throws Exception {
        mockMvc.perform(get(BeerController.BASE_URL + "/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void saveBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDto();
        beerDTO.setId(null);
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(post(BeerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDto();
        beerDTO.setId(null);
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(put(BeerController.BASE_URL + "/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isOk());
    }

    private BeerDTO getValidBeerDto() {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(123456789L)
                .price(new BigDecimal("2.99"))
                .build();
    }
}
