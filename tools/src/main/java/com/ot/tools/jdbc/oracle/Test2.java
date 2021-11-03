package com.ot.tools.jdbc.oracle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test2 {

    private static final SimpleDateFormat sd = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    public static void main(String[] args) throws ParseException {
        String s="2021年5月10日 18时20分59秒";
        Date parse = sd.parse(s);
        String format = sd.format(parse);
        System.out.println(parse);
    }

}
