package com.nguyenphucthienan.msscssm.service;

import com.nguyenphucthienan.msscssm.domain.Payment;
import com.nguyenphucthienan.msscssm.domain.PaymentEvent;
import com.nguyenphucthienan.msscssm.domain.PaymentState;
import com.nguyenphucthienan.msscssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    public static final String PAYMENT_ID_HEADER = "payment_id";

    private final PaymentRepository paymentRepository;
    private final StateMachineFactory<PaymentState, PaymentEvent> stateMachineFactory;
    private final PaymentStateChangeInterceptor paymentStateChangeInterceptor;

    @Override
    public Payment newPayment(Payment payment) {
        payment.setState(PaymentState.NEW);
        return paymentRepository.save(payment);
    }

    @Override
    @Transactional
    public StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        sendEvent(paymentId, stateMachine, PaymentEvent.PRE_AUTH_APPROVED);
        return stateMachine;
    }

    @Override
    @Transactional
    public StateMachine<PaymentState, PaymentEvent> approveAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        sendEvent(paymentId, stateMachine, PaymentEvent.AUTH_APPROVED);
        return stateMachine;
    }

    @Override
    @Transactional
    public StateMachine<PaymentState, PaymentEvent> declineAuth(Long paymentId) {
        StateMachine<PaymentState, PaymentEvent> stateMachine = build(paymentId);
        sendEvent(paymentId, stateMachine, PaymentEvent.AUTH_DECLINED);
        return stateMachine;
    }

    private void sendEvent(Long paymentId, StateMachine<PaymentState, PaymentEvent> stateMachine, PaymentEvent event) {
        Message<PaymentEvent> message = MessageBuilder.withPayload(event)
                .setHeader(PAYMENT_ID_HEADER, paymentId)
                .build();

        stateMachine.sendEvent(message);
    }

    private StateMachine<PaymentState, PaymentEvent> build(Long paymentId) {
        Payment payment = paymentRepository.getOne(paymentId);
        StateMachine<PaymentState, PaymentEvent> stateMachine = stateMachineFactory
                .getStateMachine(Long.toString(payment.getId()));

        stateMachine.stop();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(stateMachineAccess -> {
                    stateMachineAccess.addStateMachineInterceptor(paymentStateChangeInterceptor);
                    stateMachineAccess.resetStateMachine(new DefaultStateMachineContext<>(payment.getState(), null, null, null));
                });

        stateMachine.start();

        return stateMachine;
    }
}
