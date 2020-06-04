package com.nguyenphucthienan.msscbeerorderservice.service.listener;

import com.nguyenphucthienan.brewery.model.event.AllocateOrderResult;
import com.nguyenphucthienan.msscbeerorderservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerorderservice.service.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderAllocationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResult allocateOrderResult) {
        if (!allocateOrderResult.getAllocationError() && !allocateOrderResult.getPendingInventory()) {
            // Allocated normally
            beerOrderManager.beerOrderAllocationPassed(allocateOrderResult.getBeerOrderDTO());
        } else if (!allocateOrderResult.getAllocationError() && allocateOrderResult.getPendingInventory()) {
            // Pending inventory
            beerOrderManager.beerOrderAllocationPendingInventory(allocateOrderResult.getBeerOrderDTO());
        } else if (allocateOrderResult.getAllocationError()) {
            // Allocation error
            beerOrderManager.beerOrderAllocationFailed(allocateOrderResult.getBeerOrderDTO());
        }
    }
}
