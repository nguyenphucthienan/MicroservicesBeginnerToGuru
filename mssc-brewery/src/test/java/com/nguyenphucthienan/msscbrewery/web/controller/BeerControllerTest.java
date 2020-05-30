package com.nguyenphucthienan.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nguyenphucthienan.msscbrewery.service.BeerService;
import com.nguyenphucthienan.msscbrewery.web.model.BeerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BeerService beerService;

    private BeerDTO validBeerDTO;

    @BeforeEach
    public void setUp() {
        validBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Beer Name")
                .beerStyle("PALE_ALE")
                .upc(123456789012L)
                .build();
    }

    @Test
    public void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeerDTO);

        mockMvc.perform(get("/api/v1/beers/" + validBeerDTO.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeerDTO.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer Name")));
    }

    @Test
    public void saveBeer() throws Exception {
        BeerDTO beerDTO = validBeerDTO;
        beerDTO.setId(null);
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        BeerDTO savedBeerDTO = BeerDTO.builder().id(UUID.randomUUID()).beerName("Beer Name").build();
        given(beerService.saveBeer(any())).willReturn(savedBeerDTO);

        mockMvc.perform(post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateBeer() throws Exception {
        BeerDTO beerDTO = validBeerDTO;
        beerDTO.setId(null);
        String beerDTOJson = objectMapper.writeValueAsString(beerDTO);

        mockMvc.perform(put("/api/v1/beers/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDTOJson))
                .andExpect(status().isNoContent());

        then(beerService).should().updateBeer(any(), any());
    }

    @Test
    public void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beers/" + validBeerDTO.getId()))
                .andExpect(status().isNoContent());

        then(beerService).should().deleteById(any());
    }
}
