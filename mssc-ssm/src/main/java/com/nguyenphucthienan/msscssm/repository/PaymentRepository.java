package com.nguyenphucthienan.msscssm.repository;

import com.nguyenphucthienan.msscssm.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
