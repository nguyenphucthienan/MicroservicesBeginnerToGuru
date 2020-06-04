package com.nguyenphucthienan.msscbeerorderservice.service.testcomponent;

import com.nguyenphucthienan.brewery.model.event.AllocateOrderRequest;
import com.nguyenphucthienan.brewery.model.event.AllocateOrderResult;
import com.nguyenphucthienan.msscbeerorderservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void listen(Message<AllocateOrderRequest> message) {
        AllocateOrderRequest allocateOrderRequest = message.getPayload();
        allocateOrderRequest.getBeerOrderDTO().getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder()
                        .beerOrderDTO(allocateOrderRequest.getBeerOrderDTO())
                        .pendingInventory(false)
                        .allocationError(false)
                        .build());
    }
}
