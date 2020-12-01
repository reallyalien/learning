package com.ot.tools.ifelse.controller;

import com.ot.tools.ifelse.pojo.OrderDto;
import com.ot.tools.ifelse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/handler/{type}")
    public String handler(@PathVariable("type") String type) {
        OrderDto orderDto = new OrderDto();
        orderDto.setType(type);
        return orderService.handler(orderDto);
    }
}
