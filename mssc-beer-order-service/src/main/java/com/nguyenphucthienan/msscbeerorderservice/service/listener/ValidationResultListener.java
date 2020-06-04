package com.nguyenphucthienan.msscbeerorderservice.service.listener;

import com.nguyenphucthienan.brewery.model.event.ValidateOrderResult;
import com.nguyenphucthienan.msscbeerorderservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerorderservice.service.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(ValidateOrderResult validateOrderResult) {
        final UUID beerOrderId = validateOrderResult.getOrderId();

        log.debug("Validation result for BeerOrder ID: " + beerOrderId);

        beerOrderManager.processValidationResult(beerOrderId, validateOrderResult.getIsValid());
    }
}
