package com.ot.jvm.day05.method.item3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Title: Shop
 * @Description: TODO
 * @Author: 六星教育     Fame老师
 * @CreateDate: 2020/4/21
 * @Version: 1.0
 */
public class Shop {
//    private final List<Coffee> cheesesInStock = new ArrayList<>();
//    public Coffee[] getCheeses(){
//        if (cheesesInStock.size() == 0){
//            return null;
//        }
//        return (Coffee[])cheesesInStock.toArray();
//    }

//    private final List<Coffee> cheeseInStock = new ArrayList<>();
//    private static final Coffee[] EMPTY_CHEESE_ARRAY =new Coffee[0];
//    public Coffee[] getCheeses(){
//        return cheeseInStock.toArray(EMPTY_CHEESE_ARRAY);
//    }

    private final List<Coffee> cheeseInStock = new ArrayList<>();
    public List<Coffee> getCheesesList(){
        if (cheeseInStock.isEmpty()) {
            return Collections.emptyList();
        }else {
            return new ArrayList(cheeseInStock);
        }
    }
}
