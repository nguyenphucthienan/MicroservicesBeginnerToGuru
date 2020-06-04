package com.nguyenphucthienan.msscbeerorderservice.service;

import com.nguyenphucthienan.brewery.model.BeerOrderDTO;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderEventEnum;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.nguyenphucthienan.msscbeerorderservice.repository.BeerOrderRepository;
import com.nguyenphucthienan.msscbeerorderservice.sm.BeerOrderStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BeerOrderManagerImpl implements BeerOrderManager {

    public static final String ORDER_ID_HEADER = "ORDER_ID_HEADER";

    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderStateChangeInterceptor beerOrderStateChangeInterceptor;

    @Override
    @Transactional
    public BeerOrder newBeerOrder(BeerOrder beerOrder) {
        beerOrder.setId(null);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);
        BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);
        sendBeerOrderEvent(savedBeerOrder, BeerOrderEventEnum.VALIDATE_ORDER);
        return savedBeerOrder;
    }

    @Override
    public void processValidationResult(UUID beerOrderId, Boolean isValid) {
        log.debug("Process validation result for BeerOrder ID: " + beerOrderId + ". Valid: " + isValid);

        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderId);
        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            if (isValid) {
                sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATION_PASSED);
                BeerOrder validatedOrder = beerOrderRepository.findById(beerOrderId).get();
                sendBeerOrderEvent(validatedOrder, BeerOrderEventEnum.ALLOCATE_ORDER);
            } else {
                sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.VALIDATION_FAILED);
            }
        }, () -> log.error("BeerOrder not found. ID: " + beerOrderId));
    }

    @Override
    public void beerOrderAllocationPassed(BeerOrderDTO beerOrderDTO) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderDTO.getId());
        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_SUCCESS);
            updateAllocatedQuantity(beerOrderDTO);
        }, () -> log.error("BeerOrder not found. ID: " + beerOrderDTO.getId()));
    }

    @Override
    public void beerOrderAllocationPendingInventory(BeerOrderDTO beerOrderDTO) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderDTO.getId());
        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_NO_INVENTORY);
            updateAllocatedQuantity(beerOrderDTO);
        }, () -> log.error("BeerOrder not found. ID: " + beerOrderDTO.getId()));
    }

    @Override
    public void beerOrderAllocationFailed(BeerOrderDTO beerOrderDTO) {
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(beerOrderDTO.getId());
        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            sendBeerOrderEvent(beerOrder, BeerOrderEventEnum.ALLOCATION_FAILED);
        }, () -> log.error("BeerOrder not found. ID: " + beerOrderDTO.getId()));
    }

    private void updateAllocatedQuantity(BeerOrderDTO beerOrderDTO) {
        Optional<BeerOrder> allocatedOrderOptional = beerOrderRepository.findById(beerOrderDTO.getId());
        allocatedOrderOptional.ifPresentOrElse(allocatedOrder -> {
            allocatedOrder.getBeerOrderLines().forEach(beerOrderLine -> {
                beerOrderDTO.getBeerOrderLines().forEach(beerOrderLineDto -> {
                    if(beerOrderLine.getId() .equals(beerOrderLineDto.getId())){
                        beerOrderLine.setQuantityAllocated(beerOrderLineDto.getQuantityAllocated());
                    }
                });
            });

            beerOrderRepository.saveAndFlush(allocatedOrder);
        }, () -> log.error("BeerOrder not found. ID: " + beerOrderDTO.getId()));
    }

    private void sendBeerOrderEvent(BeerOrder beerOrder, BeerOrderEventEnum beerOrderEventEnum) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine = build(beerOrder);
        Message<BeerOrderEventEnum> message = MessageBuilder
                .withPayload(beerOrderEventEnum)
                .setHeader(ORDER_ID_HEADER, beerOrder.getId().toString())
                .build();

        stateMachine.sendEvent(message);
    }

    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine = stateMachineFactory
                .getStateMachine(beerOrder.getId());

        stateMachine.stop();

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(stateMachineAccess -> {
                    stateMachineAccess.addStateMachineInterceptor(beerOrderStateChangeInterceptor);
                    stateMachineAccess.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(),
                            null, null, null));
                });

        stateMachine.start();

        return stateMachine;
    }
}
