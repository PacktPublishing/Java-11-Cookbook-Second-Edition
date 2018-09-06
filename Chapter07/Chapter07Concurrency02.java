package com.packt.cookbook.ch07_concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Chapter07Concurrency02 {
    public static void main(String... args) {

        demo1_ConcurrentList();
        demo2_CopyOnWriteArrayList();
        demo3_NavigableSet();
        demo4_ConcurrentMap();
        demo5_BlockingQueueDeque();
    }

    private static void demo1_ConcurrentList() {
        System.out.println();
        System.out.println("***** ArrayList add():");
        demoListAdd(new ArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** CopyOnWriteArrayList add():");
        demoListAdd(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** ArrayList remove():");
        demoListRemove(new ArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** CopyOnWriteArrayList remove():");
        demoListRemove(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** ArrayList iter.remove():");
        demoListIterRemove(new ArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** CopyOnWriteArrayList iter.remove():");
        demoListIterRemove(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** ArrayList list.removeIf():");
        demoRemoveIf(new ArrayList<>(Arrays.asList("One", "Two", "Three")));

        System.out.println();
        System.out.println("***** CopyOnWriteArrayList list.removeIf():");
        demoRemoveIf(new CopyOnWriteArrayList<>(Arrays.asList("One", "Two", "Three")));

    }

    private static void demoListAdd(List<String> list) {
        System.out.println("list: " + list);
        try {
            for (String e : list) {
                System.out.println(e);
                if (!list.contains("Four")) {
                    System.out.println("Calling list.add(Four)...");
                    list.add("Four");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("list: " + list);
    }

    private static void demoListRemove(List<String> list) {
        System.out.println("list: " + list);
        try {
            for (String e : list) {
                System.out.println(e);
                if (list.contains("Two")) {
                    System.out.println("Calling list.remove(Two)...");
                    list.remove("Two");
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("list: " + list);
    }

    private static void demoListIterRemove(List<String> list) {
        System.out.println("list: " + list);
        try {
            Iterator iter = list.iterator();
            while (iter.hasNext()) {
                String e = (String) iter.next();
                System.out.println(e);
                if ("Two".equals(e)) {
                    System.out.println("Calling iter.remove()...");
                    iter.remove();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("list: " + list);
    }

    private static void demoRemoveIf(Collection<String> collection) {
        System.out.println("collection: " + collection);
        System.out.println("Calling list.removeIf(e -> Two.equals(e))...");
        collection.removeIf(e -> "Two".equals(e));
        System.out.println("collection: " + collection);
    }

    private static void demo2_CopyOnWriteArrayList() {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>(Arrays.asList("Five", "Six", "Seven"));
        System.out.println();
        System.out.println("list: " + list);
        System.out.println("Calling list.addIfAbsent(One)...");
        list.addIfAbsent("One");
        System.out.println("list: " + list);
    }

    private static void demo3_NavigableSet() {
        System.out.println();
        System.out.println("***** TreeSet API:");
        demoNavigableSetApi(new TreeSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** ConcurrentSkipListSet API:");
        demoNavigableSetApi(new ConcurrentSkipListSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** TreeSet set.remove(2):");
        demoNavigableSetRemove(new TreeSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** ConcurrentSkipListSet set.remove(2):");
        demoNavigableSetRemove(new ConcurrentSkipListSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** TreeSet iter.remove():");
        demoNavigableSetIterRemove(new TreeSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** ConcurrentSkipListSet iter.remove():");
        demoNavigableSetIterRemove(new ConcurrentSkipListSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** TreeSet set.add():");
        demoNavigableSetAdd(new TreeSet<>(Arrays.asList(0, 1, 2, 3)));

        System.out.println();
        System.out.println("***** ConcurrentSkipListSet set.add():");
        demoNavigableSetAdd(new ConcurrentSkipListSet<>(Arrays.asList(0, 1, 2, 3)));
    }

    private static void demoNavigableSetApi(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        System.out.println("set.descendingSet(): " + set.descendingSet());
        System.out.println();
        System.out.println("set.tailSet(2, true): " + set.tailSet(2, true));
        System.out.println("set.tailSet(2, false): " + set.tailSet(2, false));
        System.out.println("set.lower(2): " + set.lower(2));
        System.out.println("set.floor(2): " + set.floor(2));
        System.out.println("set.higher(2): " + set.higher(2));
        System.out.println("set.ceiling(2): " + set.ceiling(2));
        System.out.println();
        System.out.println("set: " + set);
        System.out.println("set.pollFirst(): " + set.pollFirst());
        System.out.println("set: " + set);
        System.out.println();
        System.out.println("set.pollLast(): " + set.pollLast());
        System.out.println("set: " + set);
    }

    private static void demoNavigableSetRemove(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        try {
            for (int i : set) {
                System.out.println(i);
                System.out.println("Calling set.remove(2)...");
                set.remove(2);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("set: " + set);
    }

    private static void demoNavigableSetIterRemove(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        try {
            Iterator iter = set.iterator();
            while (iter.hasNext()) {
                Integer e = (Integer) iter.next();
                System.out.println(e);
                if (e == 2) {
                    System.out.println("Calling iter.remove()...");
                    iter.remove();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("set: " + set);
    }

    private static void demoNavigableSetAdd(NavigableSet<Integer> set) {
        System.out.println("set: " + set);
        try {
            int m = set.stream().max(Comparator.naturalOrder()).get() + 1;
            for (int i : set) {
                System.out.println(i);
                System.out.println("Calling set.add(" + m + ")");
                set.add(m++);
                if (m > 6) {
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
        System.out.println("set: " + set);
    }

    private static void demo4_ConcurrentMap() {
        System.out.println();
        System.out.println("***** HashMap map.put():");
        demoMapPut(createhMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap map.put():");
        demoMapPut(new ConcurrentHashMap(createhMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap map.put():");
        demoMapPut(new ConcurrentSkipListMap(createhMap()));

        System.out.println();
        System.out.println("***** HashMap map.remove(2):");
        demoMapRemove(createhMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap map.remove():");
        demoMapRemove(new ConcurrentHashMap(createhMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap map.remove():");
        demoMapRemove(new ConcurrentSkipListMap(createhMap()));

        System.out.println();
        System.out.println("***** HashMap iter.remove():");
        demoMapIterRemove(createhMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap iter.remove():");
        demoMapIterRemove(new ConcurrentHashMap(createhMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap iter.remove():");
        demoMapIterRemove(new ConcurrentSkipListMap(createhMap()));

        System.out.println();
        System.out.println("***** HashMap map.keySet().remove():");
        demoMapKeySetRemove(createhMap());

        System.out.println();
        System.out.println("***** ConcurrentHashMap map.keySet().remove():");
        demoMapKeySetRemove(new ConcurrentHashMap(createhMap()));

        System.out.println();
        System.out.println("***** ConcurrentSkipListMap map.keySet().remove():");
        demoMapKeySetRemove(new ConcurrentSkipListMap(createhMap()));

    }

    private static Map createhMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "Zero");
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");
        return map;
    }

    private static void demoMapRemove(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            for (int i : map.keySet()) {
                System.out.println(i);

                System.out.println("Calling map.remove(2)...");
                String result = map.remove(2);
                System.out.println("removed: " + result);

                System.out.println("Calling map.remove(2, Two)...");
                boolean success = map.remove(2, "Two");
                System.out.println("removed: " + success);

                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }

    private static void demoMapKeySetRemove(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            for (int i : map.keySet()) {
                System.out.println(i);

                System.out.println("Calling map.keySet().remove(2)...");
                boolean result = map.keySet().remove(2);
                System.out.println("removed: " + result);
                System.out.println("map: " + map);

                System.out.println("Calling map.keySet().removeIf(e -> e == 2)...");
                result = map.keySet().removeIf(e -> e == 2);
                System.out.println("removed: " + result);
                System.out.println("map: " + map);

                System.out.println("Calling map.keySet().removeIf(e -> e == 3)...");
                result = map.keySet().removeIf(e -> e == 3);
                System.out.println("removed: " + result);

                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }

    private static void demoMapIterRemove(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            Iterator iter = map.keySet().iterator();
            while (iter.hasNext()) {
                Integer e = (Integer) iter.next();
                System.out.println(e);
                if (e == 2) {
                    System.out.println("Calling iter.remove()...");
                    iter.remove();
                }
                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }

    private static void demoMapPut(Map<Integer, String> map) {
        System.out.println("map: " + map);
        try {
            Set<Integer> keys = map.keySet();
            for (int i : keys) {
                System.out.println(i);
                System.out.println("Calling map.put(8, Eight)...");
                map.put(8, "Eight");

                System.out.println("map: " + map);
                System.out.println("Calling map.put(8, Eight)...");
                map.put(8, "Eight");

                System.out.println("map: " + map);
                System.out.println("Calling map.putIfAbsent(9, Nine)...");
                map.putIfAbsent(9, "Nine");

                System.out.println("map: " + map);
                System.out.println("Calling map.putIfAbsent(9, Nine)...");
                map.putIfAbsent(9, "Nine");

                System.out.println("keys.size(): " + keys.size());
                System.out.println("map: " + map);
            }
        } catch (Exception ex) {
            System.out.println(ex.getClass().getName());
        }
    }

    private static void demo5_BlockingQueueDeque() {
        System.out.println();
        BlockingQueue<QueueElement> queue = new ArrayBlockingQueue<>(5);
        QueueProducer producer = new QueueProducer(5, 2, queue);
        QueueConsumer consumer1 = new QueueConsumer("First",3, queue);
        QueueConsumer consumer2 = new QueueConsumer("Second",4, queue);
        new Thread(producer).start();
        new Thread(consumer1).start();
        new Thread(consumer2).start();
    }

    private static class QueueElement {
        private String value;
        public QueueElement(String value){
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }

    private static class QueueProducer implements Runnable {
        int intervalMs, consumersCount;
        private BlockingQueue<QueueElement> queue;

        public QueueProducer(int intervalMs, int consumersCount, BlockingQueue<QueueElement> queue) {
            this.consumersCount = consumersCount;
            this.intervalMs = intervalMs;
            this.queue = queue;
        }

        public void run() {
            List<String> list = List.of("One", "Two", "Three", "Four", "Five");
            try {
                for (String e : list) {
                    Thread.sleep(intervalMs);
                    queue.put(new QueueElement(e));
                    System.out.println(e + " produced" );
                }
                for(int i = 0; i < consumersCount; i++){
                    queue.put(new QueueElement("Stop"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static class QueueConsumer implements Runnable{
        private String name;
        private int intervalMs;
        private BlockingQueue<QueueElement> queue;

        public QueueConsumer(String name, int intervalMs, BlockingQueue<QueueElement> queue){
            this.intervalMs = intervalMs;
            this.queue = queue;
            this.name = name;
        }
        public void run() {
            try {
                while(true){
                    String value = queue.take().getValue();
                    if("Stop".equals(value)){
                        break;
                    }
                    System.out.println(value + " consumed by " + name);
                    Thread.sleep(intervalMs);
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}


