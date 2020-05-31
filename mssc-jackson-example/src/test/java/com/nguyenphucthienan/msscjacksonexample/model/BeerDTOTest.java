package com.nguyenphucthienan.msscjacksonexample.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@JsonTest
public class BeerDTOTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDTO() throws JsonProcessingException {
        BeerDTO beerDTO = getValidBeerDTO();
        String jsonString = objectMapper.writeValueAsString(beerDTO);
        log.info("JSON String: {}", jsonString);
    }

    @Test
    public void testDeserializeDTO() throws JsonProcessingException {
        String jsonString = "{\"beerName\":\"Beer Name\",\"beerStyle\":\"Ale\",\"upc\":123456789,\"price\":\"2.99\",\"createdDate\":\"2020-05-31-10:21:37+0700\",\"lastUpdatedDate\":\"2020-05-31-10:21:37+0700\",\"beerId\":\"3d2fff7a-6ef0-4d97-8e91-70bd6a21bcf6\"}";
        BeerDTO beerDTO = objectMapper.readValue(jsonString, BeerDTO.class);
        log.info("BeerDTO: {}", beerDTO);
        assertNotNull(beerDTO);
    }
}
