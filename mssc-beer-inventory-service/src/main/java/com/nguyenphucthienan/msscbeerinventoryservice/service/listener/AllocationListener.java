package com.nguyenphucthienan.msscbeerinventoryservice.service.listener;

import com.nguyenphucthienan.brewery.model.event.AllocateOrderRequest;
import com.nguyenphucthienan.brewery.model.event.AllocateOrderResult;
import com.nguyenphucthienan.msscbeerinventoryservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerinventoryservice.service.AllocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationListener {

    private final AllocationService allocationService;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(AllocateOrderRequest allocateOrderRequest) {
        AllocateOrderResult.AllocateOrderResultBuilder builder = AllocateOrderResult.builder();
        builder.beerOrderDTO(allocateOrderRequest.getBeerOrderDTO());

        try {
            Boolean allocationResult = allocationService.allocateOrder(allocateOrderRequest.getBeerOrderDTO());
            builder.pendingInventory(!allocationResult);
            builder.allocationError(false);
        } catch (Exception e) {
            log.error("Allocation failed for BeerOrder ID:" + allocateOrderRequest.getBeerOrderDTO().getId());
            builder.allocationError(true);
        }

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, builder.build());
    }
}
