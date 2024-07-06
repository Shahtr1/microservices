package com.shahbytes.CommonService.commands;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value // to create immutable class like Record
public class CancelOrderCommand {
    @TargetAggregateIdentifier
    String orderId;

    String orderStatus = "CANCELLED";
}
