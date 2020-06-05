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

        boolean pendingInventory = false;
        boolean allocationError = false;

        if (allocateOrderRequest.getBeerOrderDTO().getCustomerRef() != null
                && allocateOrderRequest.getBeerOrderDTO().getCustomerRef().equals("partial-allocation")){
            pendingInventory = true;
        }

        if (allocateOrderRequest.getBeerOrderDTO().getCustomerRef() != null
                && allocateOrderRequest.getBeerOrderDTO().getCustomerRef().equals("fail-allocation")){
            allocationError = true;
        }

        boolean finalPendingInventory = pendingInventory;
        allocateOrderRequest.getBeerOrderDTO().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if (finalPendingInventory) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                AllocateOrderResult.builder()
                        .beerOrderDTO(allocateOrderRequest.getBeerOrderDTO())
                        .pendingInventory(pendingInventory)
                        .allocationError(allocationError)
                        .build());
    }
}
