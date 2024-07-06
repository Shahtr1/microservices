package com.shahbytes.CommonService.events;

import lombok.Data;

@Data
public class OrderCancelledEvent {
    String orderId;
    String orderStatus;
}
