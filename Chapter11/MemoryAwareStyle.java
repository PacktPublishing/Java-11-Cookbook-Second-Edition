package com.packt.cookbook.ch11_memory;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

public class MemoryAwareStyle {
    public static void main(String... args) {
        //cacheLimitSize();
        //weakHashMap1();
        //weakHashMap2();
        //weakHashMap3();
        //weakHashMap4();
        //weakReference();
        //softReference();

        //pauseSec(30_000);
        int count = 10_000;
        //stringPlus(count);
        //stringBuilder(count);
        stringBuffer(count);


    }

    private static void pauseMs(int ms){
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void stringBuffer(int count) {
        long um = usedMemoryMB();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < count; i++ ){
            sb.append(Integer.toString(i)).append(" ");
        }
        System.out.println("Used memory: " + (usedMemoryMB() - um) + " MB"); //1 MB
    }

    private static void stringBuilder(int count) {
        long um = usedMemoryMB();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; i++ ){
            sb.append(Integer.toString(i)).append(" ");
        }
        System.out.println("Used memory: " + (usedMemoryMB() - um) + " MB"); //1 MB
    }

    private static void stringPlus(int count) {
        long um = usedMemoryMB();
        String s = "";
        for(int i = 1000; i < count; i++ ){
            s += Integer.toString(i);
            s += " ";
        }
        System.out.println("Used memory: " + (usedMemoryMB() - um) + " MB"); //71 MB
    }

    private static SoftReference<Map<Integer, Double[]>> cache3;

    private static void softReference() {
        Map<Integer, Double[]> map = new HashMap<>();
        cache3 = new SoftReference<>(map);
        map = null;

        int cacheSize = 0;
        List<Double[]> list = new ArrayList<>();
        for(int i = 0; i < 10_000_000; i++) {
            Double[] d = new Double[1024];
            list.add(d);
            if (cache3.get() != null) {
                cache3.get().put(i, d);
                cacheSize = cache3.get().size();
                System.out.println("Cache="+cacheSize + ", used memory=" + usedMemoryMB()+" MB"); //Cache=1004737, used memory=4096 MB
            } else {
                System.out.println(i + ": cache.get()==" + cache3.get()); //1_004_737: cache.get()==null
                break;
            }
        }
    }

    private static WeakReference<Map<Integer, Double[]>> cache2;

    private static void weakReference() {
        Map<Integer, Double[]> map = new HashMap<>();
        cache2 = new WeakReference<>(map);
        map = null;

        int cacheSize = 0;
        List<Double[]> list = new ArrayList<>();
        for(int i = 0; i < 10_000_000; i++) {
            Double[] d = new Double[1024];
            list.add(d);
            if (cache2.get() != null) {
                cache2.get().put(i, d);
                cacheSize = cache2.get().size();
                System.out.println("Cache="+cacheSize + ", used memory=" + usedMemoryMB()+" MB"); //Cache=4582, used memory=25 MB
            } else {
                System.out.println(i + ": cache.get()==" + cache2.get()); //4582: cache.get()==null
                break;
            }
        }
    }

    private static WeakHashMap<Integer, Double> cache1 = new WeakHashMap<>();

    private static void weakHashMap4() {
        int last = 0;
        int cacheSize = 0;
        List<WeakReference<Integer>> list = new ArrayList<>();
        for(int i = 0; i < 1000_000_000; i++) {
            Integer iObj = i;
            cache1.put(iObj, Double.valueOf(i));
            list.add(new WeakReference(iObj));
            cacheSize = cache1.size();
            //System.out.println("Used memory=" + usedMemoryMB()+" MB, cache="  + cacheSize);
            if(cacheSize < last){
                System.out.println("Used memory=" + usedMemoryMB()+" MB, cache="  + cacheSize + ", list=" + list.size());
            }
            last = cacheSize;
        }
    }

    private static void weakHashMap3() {
        int last = 0;
        int cacheSize = 0;
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 100_000_000; i++) {
            Integer iObj = i;
            cache1.put(iObj, Double.valueOf(i));
            list.add(iObj);
            cacheSize = cache1.size();
            //System.out.println("Used memory=" + usedMemoryMB()+" MB, cache="  + cacheSize);
            if(cacheSize < last){
                System.out.println("Used memory=" + usedMemoryMB()+" MB, cache="  + cacheSize);
            }
            last = cacheSize;
        }
    }

    private static void weakHashMap2() {
        int last = 0;
        int cacheSize = 0;
        for(int i = 0; i < 100_000_000; i++) {
            Integer iObj = i;
            cache1.put(iObj, Double.valueOf(i));
            //iObj = null;
            cacheSize = cache1.size();
            if(cacheSize < last){
                System.out.println("Used memory=" + usedMemoryMB()+" MB, cache="  + cacheSize);
            }
            last = cacheSize;
        }
    }

    private static void weakHashMap1() {
        int last = 0;
        int cacheSize = 0;
        for(int i = 0; i < 100_000_000; i++) {
            cache1.put(i, Double.valueOf(i));
            cacheSize = cache1.size();
            if(cacheSize < last){
                System.out.println("Used memory=" + usedMemoryMB()+" MB, cache="  + cacheSize);
            }
            last = cacheSize;
        }
    }


    private static long usedMemoryMB() {
        return Math.round(Double.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1024/1024);
    }

    private static void cacheLimitSize(){
        for(int i = 0; i < 100; i++){
            Object obj = getSomeData3(String.valueOf(i));
            System.out.println(cache.size());
        }
    }
    private static HashMap<String, Object> cache = new HashMap<>();
    private static HashMap<String, Integer> count = new HashMap<>();

    public static Object getSomeData3(String someKey) {
        Object obj = cache.get(someKey);
        if(obj == null){
            obj = ""; //get data from some source
            cache.put(someKey, obj);
            count.put(someKey, 1);
            if(cache.size() > 10){
                Map.Entry<String, Integer> max = count.entrySet().stream()
                        .max(Map.Entry.comparingByValue(Integer::compareTo))
                        .get();
                cache.remove(max.getKey());
                count.remove(max.getKey());
            }
        } else {
            count.put(someKey, count.get(someKey) + 1);
        }
        return obj;
    }

    public Object getSomeData2(String someKey) {
        Object obj = cache.get(someKey);
        if(obj == null){
            obj = ""; //get data from some source
            cache.put(someKey, obj);
        }
        return obj;
    }

    static {
        //populate the cache here
    }
    public Object getSomeData1(String someKey) {
        Object obj = cache.get(someKey);
        cache.remove(someKey);
        return obj;
    }

    private static class Calculator {
        public  double calculate(int i) {
            return Math.sqrt(2.0 * i);
        }
    }

    private static class ExpensiveInitClass {
        private Object data;
        public ExpensiveInitClass() {
            //code that consumes resources
            //and assignes value to this.data
        }

        public Object getData(){
            return this.data;
        }
    }

    private static class LazyInitExample {
        public ExpensiveInitClass expensiveInitClass;

        public Object getData(){  //can synchrnonize here
            if(this.expensiveInitClass == null){
                synchronized (LazyInitExample.class) {
                    if (this.expensiveInitClass == null) {
                        this.expensiveInitClass = new ExpensiveInitClass();
                    }
                }
            }
            return expensiveInitClass.getData();
        }
    }

    private Calculator calculator;
    private Calculator getCalculator(){
        if(this.calculator == null){
            this.calculator = new Calculator();
        }
        return this.calculator;
    }

    private void reuseObject2() {
        for(int i = 0; i < 100; i++ ){
            double r = getCalculator().calculate(i);
            //use result r
        }

    }

    private static void reuseObject() {
        Calculator calculator = new Calculator();
        for(int i = 0; i < 100; i++ ){
            double r = calculator.calculate(i);
            //use result r
        }

    }

}
