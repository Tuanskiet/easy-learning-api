package com.poly.EasyLearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.EasyLearning.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Các phương thức tùy chỉnh nếu cần thiết
}