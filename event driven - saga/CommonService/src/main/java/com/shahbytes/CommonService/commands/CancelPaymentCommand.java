package com.shahbytes.CommonService.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelPaymentCommand {
    @TargetAggregateIdentifier
    String paymentId;
    String orderId;
    String paymentStatus = "CANCELLED";
}
