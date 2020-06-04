package com.nguyenphucthienan.msscbeerorderservice.repository;

import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrder;
import com.nguyenphucthienan.msscbeerorderservice.domain.BeerOrderStatusEnum;
import com.nguyenphucthienan.msscbeerorderservice.domain.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatus(BeerOrderStatusEnum orderStatusEnum);
}
