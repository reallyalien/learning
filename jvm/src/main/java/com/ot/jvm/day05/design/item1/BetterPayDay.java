package com.ot.jvm.day05.design.item1;

/**
 * 策略枚举（枚举中有枚举）
 * 类说明：加班费计算,工作日加班2倍，节假日3倍
 * @Title: BetterPayDay
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/22
 * @Version: 1.0
 */
public enum BetterPayDay {
    MONDAY(PayType.WORK), TUESDAY(PayType.WORK), WEDNESDAY(
            PayType.WORK), THURSDAY(PayType.WORK), FRIDAY(PayType.WORK),
    SATURDAY(PayType.REST), SUNDAY(PayType.REST);

    private final PayType payType;
    BetterPayDay(PayType payType) {
        this.payType = payType;
    }
    double pay(double hoursOvertime) {
        return payType.pay(hoursOvertime);
    }

    //内部枚举：计算加班费
    private enum PayType {
        WORK {
            double pay(double hoursOvertime) {
                return hoursOvertime*HOURS_WORK;
            }
        },
        REST {
            double pay(double hoursOvertime) {
                return hoursOvertime*HOURS_REST;
            }
        };

        private static final int HOURS_WORK = 200; //工作日每小时加班费
        private static final int HOURS_REST = 300; //休息日每小时加班费

        abstract double pay(double hoursOvertime);
    }

    public static void main(String[] args) {
        System.out.println(BetterPayDay.MONDAY.pay(2));
    }
}
