package com.nguyenphucthienan.msscbeerorderservice.sm.action;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.nguyenphucthienan.msscbeerorderservice.service.BeerOrderManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        log.error("Compensating Transaction. Validation Failed. BeerOrder ID: " + beerOrderId);
    }
}
