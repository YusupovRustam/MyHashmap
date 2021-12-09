package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        MyHashMap<String, Integer> map1 = new MyHashMap<>();
        map.put("xx", 66);

        map.putIfAbsent("ww",222222222);
        System.out.println(map);
    }
}
