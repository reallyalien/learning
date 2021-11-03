package com.ot.netty.dubborpc.customer;


import com.ot.netty.dubborpc.common.Constant;
import com.ot.netty.dubborpc.netty.NettyClient;
import com.ot.netty.dubborpc.common.HelloService;

public class ClientBootstrap {

    public static void main(String[] args) throws  Exception{

        //创建一个消费者
        NettyClient customer = new NettyClient();

        //创建代理对象
        HelloService service = (HelloService) customer.getBean(HelloService.class, Constant.PROVIDERNAME);

        for (;; ) {
            Thread.sleep(2 * 1000);
            //通过代理对象调用服务提供者的方法(服务)
            String res = service.hello("你好 dubbo~");
            System.out.println("调用的结果 res= " + res);
        }
    }
}
