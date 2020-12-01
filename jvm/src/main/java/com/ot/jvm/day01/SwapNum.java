package com.ot.jvm.day01;

import java.lang.reflect.Field;

public class SwapNum {
    /**
     * 基本类型和包装类型生成的get、set方法不一样
     */

    private Boolean isSuccess;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }
    private boolean isFail;

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a = 10;
        Integer b = 20;
        System.out.println("交换前：a=" + a + "，b=" + b);
        swap(a,b);
        System.out.println("交换后：a=" + a + "，b=" + b);
    }

    public static void swap(Integer value1, Integer value2) throws NoSuchFieldException, IllegalAccessException {
        Field field = Integer.class.getDeclaredField("value");
        field.setAccessible(true);
        //这里一定要new，否则在-128到127之间的数会重新读缓存，导致数据交互失败
        Integer newValue = new Integer(value1.intValue());
        //修改引用的值
        field.set(value1,value2);
        field.set(value2,newValue);
    }
}
