package com.ot.tools.util.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class B {

    public static void main(String[] args) {
        String a = "01:00";
        String b = "09:30-10:30";
        String parse = parse(a);
        String parse1 = parse(b);
    }

    public static String parse(String string) {
        if (string.contains("-")) {
            string = string.substring(0, string.indexOf("-"));
        }
        return string + ":00";
    }
}
