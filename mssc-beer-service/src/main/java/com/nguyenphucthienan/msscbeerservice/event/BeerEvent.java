package com.nguyenphucthienan.msscbeerservice.event;

import com.nguyenphucthienan.msscbeerservice.web.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    private static final long serialVersionUID = 2328228695155945035L;

    private BeerDTO beerDTO;
}
