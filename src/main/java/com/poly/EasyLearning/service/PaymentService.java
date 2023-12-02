package com.poly.EasyLearning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.EasyLearning.entity.Payment;
import com.poly.EasyLearning.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void insert(Payment payment) {
        paymentRepository.save(payment);
    }
}
