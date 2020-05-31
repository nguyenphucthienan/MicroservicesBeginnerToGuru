package com.nguyenphucthienan.msscjacksonexample.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@JsonTest
@ActiveProfiles("kebab-case")
public class BeerDTOKebabCaseTest extends BaseTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDTO() throws JsonProcessingException {
        BeerDTO beerDTO = getValidBeerDTO();
        String jsonString = objectMapper.writeValueAsString(beerDTO);
        log.info("JSON String: {}", jsonString);
    }
}
