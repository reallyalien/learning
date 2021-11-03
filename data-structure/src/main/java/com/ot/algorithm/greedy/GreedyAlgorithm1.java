package com.ot.algorithm.greedy;

import java.util.*;

/**
 * 贪心算法
 */
public class GreedyAlgorithm1 {

    public static void main(String[] args) {
        //创建广播电台
        Map<String, HashSet<String>> broadCasts = new HashMap<>();
        HashSet<String> set1 = new HashSet<>();
        HashSet<String> set2 = new HashSet<>();
        HashSet<String> set3 = new HashSet<>();
        HashSet<String> set4 = new HashSet<>();
        HashSet<String> set5 = new HashSet<>();
        Collections.addAll(set1, "北京", "上海", "天津");
        Collections.addAll(set2, "广州", "深圳", "北京");
        Collections.addAll(set3, "成都", "上海", "杭州");
        Collections.addAll(set4, "上海", "天津");
        Collections.addAll(set5, "杭州", "大连");
        broadCasts.put("k1", set1);
        broadCasts.put("k2", set2);
        broadCasts.put("k3", set3);
        broadCasts.put("k4", set4);
        broadCasts.put("k5", set5);
        Set<Map.Entry<String, HashSet<String>>> entries = broadCasts.entrySet();
        for (Map.Entry<String, HashSet<String>> entry : entries) {
            System.out.println(entry.getKey() + "->" + entry.getValue());
        }
        System.out.println();

        //start
        Set<String> allAreas = new HashSet<>();//所有地区
        allAreas.addAll(set1);
        allAreas.addAll(set2);
        allAreas.addAll(set3);
        allAreas.addAll(set4);
        allAreas.addAll(set5);
        System.out.println(allAreas);

        //选择的电台集合
        List<String> selectList = new ArrayList<>();
        //临时集合，保存在遍历过程当中，存放电台覆盖的地区和还没有覆盖区域的交集
        Set<String> temp = new HashSet<>();
        //在遍历过程当中，能够覆盖的最多未覆盖的地区的对应的地区的key
        //if maxKey不为空，就加入到selectList
        String maxKey = null;
        while (!allAreas.isEmpty()) {
            maxKey = null;
            for (String key : broadCasts.keySet()) {
                temp.clear();
                temp.addAll(broadCasts.get(key));
                temp.retainAll(allAreas);
                if (temp.size() > 0 && (maxKey == null || temp.size() > broadCasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }
            if (null != maxKey) {
                selectList.add(maxKey);
                allAreas.removeAll(broadCasts.get(maxKey));
                broadCasts.remove(maxKey);
            }
        }
        System.out.println(selectList);
    }
}
