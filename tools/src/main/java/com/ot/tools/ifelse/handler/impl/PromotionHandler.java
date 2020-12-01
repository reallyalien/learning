package com.ot.tools.ifelse.handler.impl;

import com.ot.tools.ifelse.anno.HandlerType;
import com.ot.tools.ifelse.enums.HandlerTypeEnum;
import com.ot.tools.ifelse.handler.HandlerInterface;
import com.ot.tools.ifelse.pojo.OrderDto;
import org.springframework.stereotype.Component;

@Component
@HandlerType(HandlerTypeEnum.promotion)
public class PromotionHandler implements HandlerInterface {
    @Override
    public String handler(OrderDto orderDto) {
        return "处理促销订单";
    }
}
