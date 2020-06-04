package com.nguyenphucthienan.msscbeerorderservice.sm.action;

import com.nguyenphucthienan.brewery.model.event.ValidateOrderRequest;
import com.nguyenphucthienan.msscbeerorderservice.config.JmsConfig;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.nguyenphucthienan.msscbeerorderservice.repository.BeerOrderRepository;
import com.nguyenphucthienan.msscbeerorderservice.service.BeerOrderManagerImpl;
import com.nguyenphucthienan.msscbeerorderservice.web.mapper.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository
                .findById(UUID.fromString(Objects.requireNonNull(beerOrderId)));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                    .beerOrderDTO(beerOrderMapper.beerOrderToBeerDTO(beerOrder))
                    .build());
            log.debug("Sent validation request to queue for BeerOrder ID " + beerOrderId);
        }, () -> log.error("BeerOrder not found. ID: " + beerOrderId));
    }
}
