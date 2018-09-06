package com.packt.cookbook.ch15_new_way.b_lambdas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Chapter15Upgrades {
    public static void main(String... args) {
        Function<String, Integer> noneByDefault = notUsed -> 0; // currently
       // Function<String, Integer> noneByDefault1 = _ -> 0; // proposed


        Map<String, Integer> map = new HashMap<>();
                String key = "theInitialKey";

/*
        map.computeIfAbsent(key, _ -> {
            String key = "theShadowKey"; // shadow variable
            return key.length();
        });
*/

    }
}
