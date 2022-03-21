package com.ot.tools.anno;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class C {
    public static void main(String[] args) {
        String s="{\"bookCode\":\"12572079\",\"prescription\":\"171\",\"chargeId\":\"12572079\",\"hisOperTime\":1637981060855,\"amt\":\"15.00\",\"presType\":\"44\"}";
        JSONObject jsonObject = JSON.parseObject(s);
        Object chargeId = jsonObject.get("chargeId");
        System.out.println(chargeId);


        try {

            throw new RuntimeException("aaa");
        }catch (Exception e){
            System.out.println(1);
            e.printStackTrace();
        }
    }
}
