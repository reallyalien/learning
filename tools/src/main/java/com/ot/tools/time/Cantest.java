package com.ot.tools.time;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cantest {
    public static void main(String[] args) throws ParseException {
        for (int i = 0; i <= 3; i++) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(date);
            date = sdf.parse(dateStr);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE,i);
            int b = c.get(Calendar.DAY_OF_WEEK);
            System.out.println(b-1);
        }


    }
}
