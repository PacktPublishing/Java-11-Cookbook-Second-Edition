package com.packt.cookbook.ch11_memory;

import java.util.ArrayList;
import java.util.List;

public class Epsilon {
    public static void main(String... args) {
        List<byte[]> list = new ArrayList<>();
        int n = 4 * 1024 * 1024;
        for(int i=0; i < n; i++){
            list.add(new byte[1024]);
            byte[] arr = new byte[1024];
        }

    }

}
