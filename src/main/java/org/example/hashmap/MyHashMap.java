package org.example.hashmap;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现一个最简单的HashMap
 */
public class MyHashMap {

    public static void main(String[] args) {
        // 初始化一组字符串
        List<String> list = new ArrayList<>();
        list.add("jlkk");
        list.add("lopi");
        list.add("小傅哥");
        list.add("e4we");
        list.add("alpo");
        list.add("yhjk");
        list.add("plop");

        // 1. index的计算方法
        // 2. index冲突的解决方法
        String[] tab = new String[8];
        for(String key : list) {
            int index = key.hashCode() & (tab.length - 1); // 在tab.length为2的次幂时等价于 mod tab.length
            System.out.println(String.format("key为: %s, index为%d", key, index));
            if(tab[index] == null) {
                // 没有冲突
                tab[index] = key;
                continue;
            }
            // 存在冲突
            tab[index] = tab[index] + "->" + key;
        }
        System.out.println("测试结果： -->  " + JSONObject.toJSONString(tab));
    }

}
