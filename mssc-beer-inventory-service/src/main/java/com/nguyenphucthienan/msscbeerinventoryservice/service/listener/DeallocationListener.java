package com.nguyenphucthienan.msscbeerinventoryservice.service.listener;

import com.nguyenphucthienan.brewery.model.event.DeallocateOrderRequest;
import com.nguyenphucthienan.msscbeerinventoryservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerinventoryservice.service.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocationListener {

    private final AllocationService allocationService;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(DeallocateOrderRequest deallocateOrderRequest) {
        allocationService.deallocateOrder(deallocateOrderRequest.getBeerOrderDTO());
    }
}
