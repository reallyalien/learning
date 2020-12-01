package com.ot.tools.ifelse.enums;

public enum HandlerTypeEnum {

    normal("1"),
    group("2"),
    promotion("3");

    private String type;

    HandlerTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
