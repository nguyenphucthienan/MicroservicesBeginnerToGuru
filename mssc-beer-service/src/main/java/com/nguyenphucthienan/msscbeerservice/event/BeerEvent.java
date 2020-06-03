package com.nguyenphucthienan.msscbeerservice.event;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import lombok.*;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 2328228695155945035L;

    private final BeerDTO beerDTO;
}
