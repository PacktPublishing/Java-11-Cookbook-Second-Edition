package com.packt.cookbook.ch15_new_way.a_var;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chapter15Var {
    public static void main(String... args) {
        var list1 = new ArrayList();
        var list2 = new ArrayList<String>();

        Map<Integer, List<String>> map1 = new HashMap<>();
        //...
        for(Map.Entry<Integer, List<String>> e: map1.entrySet()){
            List<String> names = e.getValue();
            //...
        }

        var map2 = new HashMap<Integer, List<String>>();
        //...
        for(var e: map1.entrySet()){
            var names = e.getValue();
            //...
        }

        A a1 = new AImpl();
        a1.m();
        //a1.f();  //does nto compile

        var a = new AImpl();
        a.m();
        a.f();

        //var arr = {1,2,3};
        int[] arr = {1,2,3};
        var ar = new int[2];
        System.out.println(ar[0]);

        for(var i = 0; i < 10; i++){
            //...
        }

        var aImpl = new A(){
            @Override
            public void m(){
                //...
            }

        };

        var var = 1;
    }

    public void var(int i){
        ///
    }

    interface A {
        void m();
    }

    static class AImpl implements A {
        public void m(){}
        public void f(){}
    }

}