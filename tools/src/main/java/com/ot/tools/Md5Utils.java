package com.ot.tools;

import java.io.UnsupportedEncodingException;

public class Md5Utils {
    private static final int HEX_VALUE_COUNT = 16;

    private static final String salt = "25aadbf7-7200-42b2-8466-acdb7ee34931";

    public static String getMD5(byte[] bytes) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char str[] = new char[16 * 2];
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(bytes);
            byte tmp[] = md.digest();
            int k = 0;
            for (int i = 0; i < HEX_VALUE_COUNT; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(str);
    }

    public static String getMD5(String value, String encode) {
        String result = "";
        try {
            result = getMD5(value.getBytes(encode));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String inPatientNo = "901954";
        StringBuilder sb = new StringBuilder().append("inPatinetNo=").append(inPatientNo).append("&salt=").append(salt);
        String s = sb.toString();
        System.out.println("加密前：" + s);
        String md5Str = Md5Utils.getMD5(s.getBytes()).toUpperCase();
        System.out.println("加密后：" + md5Str);

    }
}
