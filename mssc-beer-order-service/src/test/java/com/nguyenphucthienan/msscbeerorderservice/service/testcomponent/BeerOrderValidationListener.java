package com.nguyenphucthienan.msscbeerorderservice.service.testcomponent;

import com.nguyenphucthienan.brewery.model.event.ValidateOrderRequest;
import com.nguyenphucthienan.brewery.model.event.ValidateOrderResult;
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
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(Message<ValidateOrderRequest> message) {
        ValidateOrderRequest validateOrderRequest = message.getPayload();
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE, ValidateOrderResult.builder()
                .isValid(true)
                .orderId(validateOrderRequest.getBeerOrderDTO().getId())
                .build());
    }
}
