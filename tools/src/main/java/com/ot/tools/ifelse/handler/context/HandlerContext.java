package com.ot.tools.ifelse.handler.context;

import com.ot.tools.ifelse.handler.HandlerInterface;
import com.ot.tools.ifelse.utils.BeanTool;

import java.util.Map;

public class HandlerContext {
    private Map<String, Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public HandlerInterface getInstance(String type) {
        Class<HandlerInterface> clazz = handlerMap.get(type);
        if (clazz == null) {
            throw new IllegalArgumentException("not found handler for type :" + type);
        }
        return BeanTool.getBean(clazz);
    }
}
