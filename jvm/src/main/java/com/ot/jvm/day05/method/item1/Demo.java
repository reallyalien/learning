package com.ot.jvm.day05.method.item1;

import java.math.BigInteger;
import java.util.AbstractList;
import java.util.List;

/**
 * @Title: Demo
 */
public class Demo {
    BigInteger n = new BigInteger("100");
    /**
     * 求模运算
     * @param m the modulus,which must be positive
     * @return this mod m
     * @throwsArithmeticException if m is less then or equal to 0
     */
    public int mod(BigInteger m){
        if (m.signum() <= 0 ) {
            //  重用jdk异常
            throw new ArithmeticException("Modulus <=0 :  " + m);
        }
        return this.n.signum() % m.signum();
    }

    private static void sort(long[] a, int offset, int length) {
        assert a != null;
        assert offset >= 0 && offset <= a.length;
        assert length >= 0 && length <= a.length -offset;
        //AsserttionError    jvm -ea
        //do something
    }

    static List<Integer> intArrayAsList (final int[] a) {
        if (a == null) {
            throw new NullPointerException();
        }

        return new AbstractList<Integer>() {

            @Override
            public Integer get(int index) {
                return a[index];
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }
}
