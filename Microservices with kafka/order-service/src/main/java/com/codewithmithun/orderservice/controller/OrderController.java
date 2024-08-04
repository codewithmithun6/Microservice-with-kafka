package com.codewithmithun.orderservice.controller;

import com.codewithmithun.basedomains.dto.Order;
import com.codewithmithun.basedomains.dto.OrderEvent;
import com.codewithmithun.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("Pending");
        orderEvent.setMessage("Order Status is in Pending state");
        orderEvent.setOrder(String.valueOf(order));
        orderProducer.sendMessage(orderEvent);

        return "Order placed successfully...";
    }
}