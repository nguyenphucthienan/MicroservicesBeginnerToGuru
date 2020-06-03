package com.nguyenphucthienan.msscssm.service;

import com.nguyenphucthienan.msscssm.domain.Payment;
import com.nguyenphucthienan.msscssm.domain.PaymentEvent;
import com.nguyenphucthienan.msscssm.domain.PaymentState;
import com.nguyenphucthienan.msscssm.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@SpringBootTest
public class PaymentServiceImplTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Test
    @Transactional
    public void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);
        log.info("Should be NEW");
        log.info(String.valueOf(savedPayment.getState()));

        StateMachine<PaymentState, PaymentEvent> stateMachine = paymentService.preAuth(savedPayment.getId());
        log.info("Should be PRE_AUTH or PRE_AUTH_ERROR");
        log.info(String.valueOf(stateMachine.getState().getId()));

        Payment preAuthedPayment = paymentRepository.getOne(savedPayment.getId());
        log.info(String.valueOf(preAuthedPayment));
    }
}
