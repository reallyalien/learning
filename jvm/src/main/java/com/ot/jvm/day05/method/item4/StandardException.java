package com.ot.jvm.day05.method.item4;

/**
 * 优先使用标准的异常
 * @Title: StandardException
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/22
 * @Version: 1.0
 */
public class StandardException {
    public static void main(String[] args) throws Exception {
        int count =0;
        if(count<0){//调用者传递的参数不合适
            throw new IllegalArgumentException("");
        }
        int state =0;
        if(state!=1){//接收的对象状态不对
            throw new IllegalStateException("");
        }
        Object o = new Object();
        if(o==null){//接收的对象为空
            throw new NullPointerException("");
        }
        //返回结果---找不到对象！
    }
    protected boolean tryAcquire(long arg) {
        throw new UnsupportedOperationException();
    }
}
