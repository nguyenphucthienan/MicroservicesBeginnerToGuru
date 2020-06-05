package com.nguyenphucthienan.msscbeerorderservice.domain;

public enum BeerOrderStatusEnum {

    NEW,
    CANCELLED,
    VALIDATED,
    VALIDATION_PENDING,
    VALIDATION_EXCEPTION,
    ALLOCATION_PENDING,
    ALLOCATED,
    ALLOCATION_EXCEPTION,
    PENDING_INVENTORY,
    PICKED_UP,
    DELIVERED,
    DELIVERY_EXCEPTION
}
