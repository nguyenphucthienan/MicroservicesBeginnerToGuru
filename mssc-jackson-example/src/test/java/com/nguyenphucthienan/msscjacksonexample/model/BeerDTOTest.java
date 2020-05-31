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
        String jsonString = "{\"id\":\"b026a091-1b21-4565-93b8-1a38c38485de\",\"beerName\":\"Beer Name\",\"beerStyle\":\"Ale\",\"upc\":123456789,\"price\":2.99,\"createdDate\":\"2020-05-31T09:26:35.9345435+07:00\",\"lastUpdatedDate\":\"2020-05-31T09:26:35.9345435+07:00\"}";
        BeerDTO beerDTO = objectMapper.readValue(jsonString, BeerDTO.class);
        log.info("BeerDTO: {}", beerDTO);
        assertNotNull(beerDTO);
    }
}
