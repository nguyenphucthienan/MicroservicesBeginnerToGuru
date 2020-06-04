package com.nguyenphucthienan.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyenphucthienan.msscbeerservice.bootstrap.BeerBootstrap;
import com.nguyenphucthienan.msscbeerservice.service.BeerService;
import com.nguyenphucthienan.brewery.model.BeerDTO;
import com.nguyenphucthienan.brewery.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    public void getBeer() throws Exception {
        given(beerService.getBeerById(any(), anyBoolean())).willReturn(getValidBeerDTO());
        mockMvc.perform(get("/api/v1/beers/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void saveBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        beerDTO.setId(null);
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        given(beerService.saveBeer(any())).willReturn(beerDTO);

        mockMvc.perform(post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateBeer() throws Exception {
        BeerDTO beerDTO = getValidBeerDTO();
        beerDTO.setId(null);
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDTO());

        mockMvc.perform(put("/api/v1/beers/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isOk());
    }

    private BeerDTO getValidBeerDTO() {
        return BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.ALE)
                .upc(BeerBootstrap.BEER_1_UPC)
                .price(new BigDecimal("2.99"))
                .build();
    }
}
