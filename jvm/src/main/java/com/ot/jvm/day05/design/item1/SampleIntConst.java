package com.ot.jvm.day05.design.item1;

/**
 * 枚举和int常量
 * @Title: SampleIntConst
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/22
 * @Version: 1.0
 */
public class SampleIntConst {
    //延时订单状态
    public static final int ORDER_DEPOT_UNPAY = 0; //未支付
    public static final int ORDER_DEPOT_PAID = 1; //已支付
    public static final int ORDER_DEPOT_TIMOUT = 2; //支付超时

    public static final String ORDER_DEPOT_PAID_DES = "已支付"; //已支付
    //物流订单
    public static final int ORDER_LOGISTICS_READY = 0; //准备发货
    public static final int ORDER_LOGISTICS_TRANSPORT = 1;//物流中
    public static final int ORDER_LOGISTICS_ARRIVED = 2;//已经收货

    public enum Depot{
        UNPAY("未支付",0),PAID("已付款",1),TIMOUT("支付超时",2);

        // 成员变量
        private String explain;
        private int ident;

        Depot(String explain, int ident) {
            this.explain = explain;
            this.ident = ident;
        }
    }
    public enum Logistics{
        READY("准备发货",0),TRANSPORT("物流中",1),ARRIVED("已经收货",2);
        // 成员变量
        private String explain;
        private int ident;

        Logistics(String explain, int ident) {
            this.explain = explain;
            this.ident = ident;
        }
    }

    public static void main(String[] args) {
        System.out.println(ORDER_DEPOT_UNPAY);
        //返回一个中文的状态
        System.out.println(Depot.PAID.explain);
        System.out.println(Depot.PAID.ident);
    }

}
