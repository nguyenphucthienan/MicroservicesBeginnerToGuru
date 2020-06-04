package com.nguyenphucthienan.msscbeerorderservice.web.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BeerOrderDTO extends BaseItem {

    @Builder
    public BeerOrderDTO(UUID id, Integer version, OffsetDateTime createdDate, OffsetDateTime lastModifiedDate,
                        UUID customerId, List<BeerOrderLineDTO> beerOrderLines, String orderStatus,
                        String orderStatusCallbackUrl, String customerRef) {
        super(id, version, createdDate, lastModifiedDate);
        this.customerId = customerId;
        this.beerOrderLines = beerOrderLines;
        this.orderStatus = orderStatus;
        this.orderStatusCallbackUrl = orderStatusCallbackUrl;
        this.customerRef = customerRef;
    }

    private UUID customerId;
    private String customerRef;
    private List<BeerOrderLineDTO> beerOrderLines;
    private String orderStatus;
    private String orderStatusCallbackUrl;
}
