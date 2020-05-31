package com.nguyenphucthienan.msscbeerorderservice.web.controller;

import com.nguyenphucthienan.msscbeerorderservice.service.BeerOrderService;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderDTO;
import com.nguyenphucthienan.msscbeerorderservice.web.model.BeerOrderPagedList;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers/{customerId}")
public class BeerOrderController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerOrderService beerOrderService;

    public BeerOrderController(BeerOrderService beerOrderService) {
        this.beerOrderService = beerOrderService;
    }

    @GetMapping("/orders")
    public BeerOrderPagedList listOrders(@PathVariable("customerId") UUID customerId,
                                         @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return beerOrderService.listOrders(customerId, PageRequest.of(pageNumber, pageSize));
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public BeerOrderDTO placeOrder(@PathVariable UUID customerId, @RequestBody BeerOrderDTO beerOrderDTO) {
        return beerOrderService.placeOrder(customerId, beerOrderDTO);
    }

    @GetMapping("/orders/{orderId}")
    public BeerOrderDTO getOrder(@PathVariable UUID customerId, @PathVariable UUID orderId) {
        return beerOrderService.getOrderById(customerId, orderId);
    }

    @PutMapping("/orders/{orderId}/pickup")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pickupOrder(@PathVariable UUID customerId, @PathVariable UUID orderId) {
        beerOrderService.pickupOrder(customerId, orderId);
    }
}
