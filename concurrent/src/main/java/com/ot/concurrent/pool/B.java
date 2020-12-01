package com.ot.concurrent.pool;

public class B {

    public static void main(String[] args) {
//        String str1 = "微信公众号菜鸟名企梦\n干货多多";
//        System.out.println(str1);
//        System.out.println("----------------------------------------");

//        str1 =  "微信公众号菜鸟名企梦\r干货多多";
        StringBuilder sb = new StringBuilder();
        sb.append(" create table ").append("newTableName").append("( ").append("\r");
        sb.append("\t").append("ggggg").append(",").append("\r");
        sb.append("\t").append("PRIMARY").append("\r");
        sb.append(");").append("\r");
        String s = sb.toString();
        System.out.println(s);
        System.out.println("----------------------------------------");


//        str1 = "微信公众号菜鸟名企梦\r\n干货多多";
//        System.out.println(str1);
//        System.out.println("----------------------------------------");
    }
}
