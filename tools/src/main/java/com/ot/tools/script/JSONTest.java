package com.ot.tools.script;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.inject.internal.cglib.core.$AbstractClassGenerator;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JSONTest {

    public static void main(String[] args) {
        byte[] arr = new byte[]{};
        Map<String, Object> map = new HashMap<>();
        map.put("arr", arr);
        String s = JSON.toJSONString(map);
        Map map1 = JSON.parseObject(s, Map.class);
        String o = (String) map1.get("arr");
        byte[] decode = Base64.getDecoder().decode(o);

    }
}
