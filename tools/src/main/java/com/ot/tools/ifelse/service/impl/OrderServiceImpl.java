package com.ot.tools.ifelse.service.impl;

import com.ot.tools.ifelse.handler.HandlerInterface;
import com.ot.tools.ifelse.handler.context.HandlerContext;
import com.ot.tools.ifelse.pojo.OrderDto;
import com.ot.tools.ifelse.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private HandlerContext handlerContext;

    @Override
    public String handler(OrderDto orderDto) {
        HandlerInterface handler = handlerContext.getInstance(orderDto.getType());
        return handler.handler(orderDto);
    }
}
