package com.ot.noline.graph;

/**
 * 使用换底公式
 */

/**
 *  loga B=logc B /logc A
 */
public class Log {
    public static void main(String[] args) {
        System.out.println(log(4, 2));
    }

    public static double log(double value, double base) {
        return Math.log(value) / Math.log(base);
    }
}
