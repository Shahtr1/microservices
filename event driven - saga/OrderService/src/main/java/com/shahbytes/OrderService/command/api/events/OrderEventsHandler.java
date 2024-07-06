package com.shahbytes.OrderService.command.api.events;

import com.shahbytes.CommonService.events.OrderCancelledEvent;
import com.shahbytes.CommonService.events.OrderCompletedEvent;
import com.shahbytes.OrderService.command.api.data.Order;
import com.shahbytes.OrderService.command.api.data.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventsHandler {

    private final OrderRepository orderRepository;


    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = new Order();
        BeanUtils.copyProperties(event, order);
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order
                = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());

        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        Order order
                = orderRepository.findById(event.getOrderId()).get();

        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);

    }


}
