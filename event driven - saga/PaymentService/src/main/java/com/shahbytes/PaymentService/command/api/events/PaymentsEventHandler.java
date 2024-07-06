package com.shahbytes.PaymentService.command.api.events;

import com.shahbytes.CommonService.events.PaymentCancelledEvent;
import com.shahbytes.CommonService.events.PaymentProcessedEvent;
import com.shahbytes.PaymentService.command.api.data.Payment;
import com.shahbytes.PaymentService.command.api.data.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class PaymentsEventHandler {

    private final PaymentRepository paymentRepository;


    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment
                = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .paymentStatus("COMPLETED")
                .timeStamp(new Date())
                .build();

        paymentRepository.save(payment);
    }

    @EventHandler
    public void on(PaymentCancelledEvent event) {
        Payment payment = paymentRepository.findById(event.getPaymentId()).get();
        payment.setPaymentStatus(event.getPaymentStatus());
        paymentRepository.save(payment);
    }


}
