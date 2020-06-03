package com.nguyenphucthienan.msscssm.config;

import com.nguyenphucthienan.msscssm.domain.PaymentEvent;
import com.nguyenphucthienan.msscssm.domain.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;

import java.util.UUID;

@Slf4j
@SpringBootTest
public class StateMachineConfigTest {

    @Autowired
    private StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;

    @Test
    public void testStateMachine() {
        StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory.getStateMachine(UUID.randomUUID());

        stateMachine.start();

        log.info(stateMachine.getState().toString());

        stateMachine.sendEvent(PaymentEvent.PRE_AUTHORIZE);

        log.info(stateMachine.getState().toString());

        stateMachine.sendEvent(PaymentEvent.PRE_AUTH_APPROVED);

        log.info(stateMachine.getState().toString());

        stateMachine.sendEvent(PaymentEvent.PRE_AUTH_DECLINED);

        log.info(stateMachine.getState().toString());
    }
}
