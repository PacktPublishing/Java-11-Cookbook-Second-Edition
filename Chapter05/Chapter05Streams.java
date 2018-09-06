package com.packt.cookbook.ch05_streams;

import com.packt.cookbook.ch05_streams.api.SpeedModel;
import com.packt.cookbook.ch05_streams.api.SpeedModel.RoadCondition;
import com.packt.cookbook.ch05_streams.api.TrafficUnit;
import com.packt.cookbook.ch05_streams.api.Vehicle;

import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Map.entry;

public class Chapter05Streams {

    public static void main(String... args) {
        createImmutableCollectionUsingOf();
        createImmutableCollectionUsingCopyOf();
        demo2_createStreams();
        demo3_operations();
        demo4_api_to_pipeline();
        demo5_parallel();
        filterStream();

    }

    public static void createImmutableCollectionUsingOf() {
        List<String> list = new ArrayList<>();
        list.add("This ");
        list.add("is ");
        list.add("built ");
        list.add("by ");
        list.add("list.add()");
        list.forEach(System.out::print);
        System.out.println();

        System.out.println();
        // Creating a subclass of ArrayList as an anonymous class,
        // and then initializing it using a non-static initialization block
        // and call method put() in it.
        new ArrayList<>() {{
            add("This ");
            add("is ");
            add("built ");
            add("by ");
            add("new ArrayList(){{add()}}");
        }}.forEach(System.out::print);
        System.out.println();

        System.out.println();
        Arrays.asList("This ", "is ", "created ", "by ", "Arrays.asList()")
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        Set<String> set = new HashSet<>();
        set.add("This ");
        set.add("is ");
        set.add("built ");
        set.add("by ");
        set.add("set.add() ");
        set.forEach(System.out::print);
        System.out.println();

        System.out.println();
        new HashSet<>(Arrays.asList("This ", "is ", "created ", "by ", "new HashSet(Arrays.asList()) "))
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        new HashSet<>() {{
            add("This ");
            add("is ");
            add("built ");
            add("by ");
            add("new HashSet(){{add()}} ");
        }}.forEach(System.out::print);
        System.out.println();

        System.out.println();
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "This ");
        map.put(2, "is ");
        map.put(3, "built ");
        map.put(4, "by ");
        map.put(5, "map.put()8888 ");
        map.entrySet().forEach(System.out::print);
        System.out.println();

        System.out.println();
        new HashMap<>() {{
            put(1, "This ");
            put(2, "is ");
            put(3, "built ");
            put(4, "by ");
            put(5, "new HashMap(){{put()}} ");
        }}.entrySet().forEach(System.out::print);
        System.out.println();

        //Java 9, new factory methods:
        System.out.println();
        List.of("This ", "is ", "created ", "by ", "List.of()")
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        Set.of("This ", "is ", "created ", "by ", "Set.of() ")
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        Map.of(1, "This ", 2, "is ", 3, "built ", 4, "by ", 5, "Map.of() ")
                .entrySet().forEach(System.out::print);
        System.out.println();

        System.out.println();
        Map.ofEntries(
                entry(1, "This "),
                entry(2, "is "),
                entry(3, "built "),
                entry(4, "by "),
                entry(5, "Map.ofEntries() ")
        ).entrySet().forEach(System.out::print);
        System.out.println();

        //Randomization of order of immutable Set/Map elements from run to run.
        System.out.println();
        Map.of(1, "Elements ", 2, "order ", 3, "of ", 4, "Map.of() ", 5, "changes ")
                .entrySet().forEach(System.out::print);

        //Mutable
        System.out.println();
        List<Integer> li = Arrays.asList(1, 2, 3, 4, 5);
        li.set(2, 0);
        li.forEach(System.out::print);
        li.forEach(i -> {
            int j = li.get(2);
            li.set(2, j + 1);
        });
        System.out.println();
        li.forEach(System.out::print);
        System.out.println();

        //Immutable
        List<Integer> l1 = List.of(1, 2, 3, 4, 5);
        //l1.set(2, 9); //UnsupportedOperationException

        List<String> l2 = List.of("This ", "is ", "immutable");
        //l2.add("Is it?");     //UnsupportedOperationException
        //l2.set(1, "is not");  //UnsupportedOperationException

        //No null
        //List<String> list = List.of("This ", "is ", "not ", "created ", null);

        //Set<String>
        set = Set.of("a", "b", "c");
        //set.remove("b");  //UnsupportedOperationException
        //set.add("e");     //UnsupportedOperationException
        //set = Set.of("a", "b", "c", null); //NullPointerException

        //Map<Integer, String>
        map = Map.of(1, "one", 2, "two", 3, "three");
        //map.remove(2);          //UnsupportedOperationException
        //map.put(5, "five ");    //UnsupportedOperationException
        //map = Map.of(1, "one", 2, "two", 3, null); //NullPointerException
        //map = Map.ofEntries(entry(1, "one"), null); //NullPointerException
    }

    static class A{}
    static class B extends A{}

    public static void createImmutableCollectionUsingCopyOf() {

        List<Integer> list = Arrays.asList(1,2,3);
        list = List.copyOf(list);
        //list.set(1, 0);     //UnsupportedOperationException
        //list.remove(1);     //UnsupportedOperationException

        Set<Integer> setInt = Set.copyOf(list);
        //setInt.add(42);       //UnsupportedOperationException
        //setInt.remove(3);  //UnsupportedOperationException

        Set<String> set = new HashSet<>(Arrays.asList("a","b","c"));
        set = Set.copyOf(set);
        //set.add("d");     //UnsupportedOperationException
        //set.remove("b");  //UnsupportedOperationException

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one ");
        map.put(2, "two ");
        map = Map.copyOf(map);
        //map.remove(2);          //UnsupportedOperationException
        //map.put(3, "three ");    //UnsupportedOperationException

        List<A> listA = Arrays.asList(new B(), new B());
        Set<A> setA = new HashSet<>(listA);

        List<B> listB = Arrays.asList(new B(), new B());
        setA = new HashSet<>(listB);

        //List<B> listB = Arrays.asList(new A(), new A()); //error
        //Set<B> setB = new HashSet<>(listA);  //error

    }

    public static void demo2_createStreams() {

        System.out.println();
        List.of("This ", "is ", "created ", "by ", "List.of().stream()")
                .stream().forEach(System.out::print);
        System.out.println();

        System.out.println();
        Set.of("This ", "is ", "created ", "by ", "Set.of().stream()")
                .stream().forEach(System.out::print);
        System.out.println();

        System.out.println();
        Map.of(1, "This ", 2, "is ", 3, "built ", 4, "by ", 5, "Map.of().entrySet().stream()")
                .entrySet().stream().forEach(System.out::print);
        System.out.println();

        System.out.println();
        String[] array = { "That ", "is ", "an ", "Arrays.stream(array)" };
        Arrays.stream(array).forEach(System.out::print);
        System.out.println();

        System.out.println();
        String[] array1 = { "That ", "is ", "an ", "Arrays.stream(array,incl,excl)" };
        Arrays.stream(array1, 0, 2).forEach(System.out::print);
        System.out.println();

        System.out.println();
        String[] array2 = { "That ", "is ", "a ", "Stream.of(array)" };
        Stream.of(array2).forEach(System.out::print); //Implemented by Arrays.stream()
        System.out.println();

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.print("Stream.generate().limit(3): ");
        Stream.generate(() -> "generated ").limit(3) //infinite otherwise
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.print("Stream.iterate().limit(10): ");
        Stream.iterate(0, i -> i + 1).limit(10) //infinite otherwise
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.print("Stream.iterate(Predicate < 10): ");
        Stream.iterate(0, i -> i < 10, i -> i + 1)
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.print("IntStream.range(0,9): ");
        IntStream.range(0, 9)
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.print("IntStream.rangeClosed(0,9): ");
        IntStream.rangeClosed(0, 9)
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.println("Files.list(dir): ");
        Path dir = FileSystems.getDefault().getPath("src/main/java/com/packt/cookbook/ch05_streams/");
        try(Stream<Path> stream = Files.list(dir)){
            stream.forEach(System.out::println);
        } catch (Exception ex){}

        System.out.println();
        System.out.println("Files.lines().limit(3): ");
        String file = "src/main/java/com/packt/cookbook/ch05_streams/Chapter05Streams.java";
        try(Stream<String> stream = Files.lines(Paths.get(file)).limit(3)){ //not infinite, but big
            stream.forEach(l -> { if( l.length()>0 ) System.out.println("    " + l); } );
        } catch (Exception ex){ ex.printStackTrace(); }

        System.out.println();
        Path pth = FileSystems.getDefault().getPath("src/main/java/com/packt/cookbook/ch05_streams");
        BiPredicate<Path, BasicFileAttributes> select = (p, b) -> p.getFileName().toString().contains("Factory");
        try(Stream<Path> stream = Files.find(pth, 2, select, FileVisitOption.FOLLOW_LINKS)){
            stream.map(path -> path.getFileName())
                    .forEach(System.out::println);
        } catch (Exception ex){ ex.printStackTrace(); }

        System.out.println();
        System.out.print("Pattern.compile().splitAsStream(): ");
        String phrase = "This used to be a phrase";
        Pattern.compile("\\W").splitAsStream(phrase)
                .forEach(w -> System.out.print(w + " "));
        System.out.println();
    }

    public static void demo3_operations() {

        System.out.println();
        int sum = Stream.of( 1,2,3,4,5,6,7,8,9 )
                .filter(i -> i % 2 != 0)
                .peek(i -> System.out.print(i))
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("\nsum = " + sum);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .filter(s -> s.contains("i"))
                .peek(s -> System.out.println("peek() after filter(): " + s))
                .forEach(System.out::println);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .peek(s -> {
                    s = s + "?";
                    System.out.println("peek(): " + s);
                })
                .map(s -> s + "!")
                .forEach(System.out::println);

        System.out.println();
        System.out.println("Files.lines().dropWhile().takeWhile(): ");
        String file = "src/main/java/com/packt/cookbook/ch05_streams/Chapter05Streams.java";
        try(Stream<String> stream = Files.lines(Paths.get(file))){
            stream.dropWhile(l -> !l.contains("dropWhile().takeWhile()"))
                    .takeWhile(l -> !l.contains("} catc" + "h"))
                    .forEach(System.out::println);
        } catch (Exception ex){ ex.printStackTrace(); }

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .map(s -> s.contains("i"))
                .forEach(System.out::println);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .filter(s -> s.contains("Th"))
                .flatMap(s -> Pattern.compile("(?!^)").splitAsStream(s))
                .forEach(System.out::println);

        System.out.println();
        Stream.concat(Stream.of(4,5,6), Stream.of(1,2,3))
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        Stream.of(Stream.of(4,5,6), Stream.of(1,2,3), Stream.of(7,8,9))
                .flatMap(Function.identity())
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        System.out.print("reduce(concat).forEach(): ");
        Stream.of(Stream.of(4,5,6), Stream.of(1,2,3), Stream.of(7,8,9))
                .reduce(Stream::concat)
                .orElseGet(Stream::empty)
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        Stream.of("3","2","1")
                .parallel()
                .forEach(System.out::print);

        System.out.println();
        System.out.println();

        Stream.of("3","2","1")
                .parallel()
                .forEachOrdered(System.out::print);

        System.out.println();
        System.out.println();
        Stream.of( "That ", "is ", "a ", null, "Stream.of(literals)" )
                .map(Optional::ofNullable) //Optional.of() throws NPE
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(String::toString)
                .forEach(System.out::print);
        System.out.println();

        System.out.println();
        System.out.println();

        int sum1 = Stream.of(1,2,3)
                .reduce((p,e) -> {
                    System.out.println(p);
                    return p + e;
                })
                .orElse(10);
        System.out.println("Stream.of(1,2,3).reduce(acc): " + sum1);

        System.out.println();
        System.out.println();

        int sum11 = Stream.of(1,2,1,1)
                .reduce((p,e) -> {
                    System.out.println("p="+p+", e="+e);
                    return p + e;
                }).orElse(0);
        System.out.println("Stream.of(1,2,1,1).reduce(acc): " + sum11);

        int sum2 = Stream.of(1,2,3)
                .reduce(0, (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).reduce(0,acc): " + sum2);

        System.out.println();
        System.out.println();

        String sum31 = Stream.of(1,2,3)
                .reduce("", (p,e) -> p + e.toString(), (x,y) -> x + "," + y);
        System.out.println("Stream.of(1,2,3).reduce(,acc,comb): " + sum31);

        System.out.println();
        System.out.println();

        String sum32 = Stream.of(1,2,3).parallel()
                .reduce("", (p,e) -> p + e.toString(), (x,y) -> x + "," + y);
        System.out.println("Stream.of(1,2,3).parallel.reduce(,acc,comb): " + sum32);

        System.out.println();
        System.out.println();

        String sum41 = Stream.of(1,2,3)
                .map(i -> i.toString() + ",")
                .reduce("", (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).map.reduce(,acc): " + sum41.substring(0, sum41.length()-1));

        System.out.println();
        System.out.println();

        String sum42 = Stream.of(1,2,3).parallel()
                .map(i -> i.toString() + ",")
                .reduce("", (p,e) -> p + e);
        System.out.println("Stream.of(1,2,3).parallel.map.reduce(,acc): " + sum42.substring(0, sum42.length()-1));

        System.out.println();

        Object[] os = Stream.of(1,2,3).toArray();
        Arrays.stream(os).forEach(System.out::print);

        System.out.println();
        System.out.println();

        String[] sts = Stream.of(1,2,3).map(i -> i.toString()).toArray(String[]::new);
        Arrays.stream(sts).forEach(System.out::print);

        System.out.println();
        System.out.println();

        double aa = Stream.of(1,2,3).map(Thing::new)
                .collect(Collectors.averagingInt(Thing::getSomeInt));
        System.out.println("stream(1,2,3).averagingInt(): " + aa);

        System.out.println();
        String as = Stream.of(1,2,3).map(Thing::new)
                .map(Thing::getSomeStr)
                .collect(Collectors.joining(","));
        System.out.println("stream(1,2,3).joining(,): " + as);

        System.out.println();
        String ss = Stream.of(1,2,3).map(Thing::new)
                .map(Thing::getSomeStr)
                .collect(Collectors.joining(",", "[","]"));
        System.out.println("stream(1,2,3).joining(,[,]): " + ss);
        System.out.println();
    }

    public static void demo4_api_to_pipeline() {
        System.out.println();

        double timeSec = 10.0;
        int trafficUnitsNumber = 10;

        SpeedModel speedModel =  (t, wp, hp) -> {
            double weightPower = 2.0 * hp * 746 * 32.174 / wp;
            return Math.round(Math.sqrt(t * weightPower) * 0.68);
        };

        BiPredicate<TrafficUnit, Double> limitSpeed = (tu, sp) ->
                (sp > (tu.getSpeedLimitMph() + 8.0) && tu.getRoadCondition() == RoadCondition.DRY)
                        || (sp > (tu.getSpeedLimitMph() + 5.0) && tu.getRoadCondition() == RoadCondition.WET)
                        || (sp > (tu.getSpeedLimitMph() + 0.0) && tu.getRoadCondition() == RoadCondition.SNOW);


        BiConsumer<TrafficUnit, Double> printResults = (tu, sp) ->
                System.out.println("Road " + tu.getRoadCondition() + ", tires " + tu.getTireCondition()
                        + ": " + tu.getVehicleType().getType() + " speedMph (" + timeSec + " sec)=" + sp + " mph");

        Traffic api = new TrafficImpl(Month.APRIL, DayOfWeek.FRIDAY, 17, "USA", "Denver", "Main103S");
        api.speedAfterStart(timeSec, trafficUnitsNumber, speedModel, limitSpeed, printResults);

        System.out.println();

        List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber, Month.APRIL, DayOfWeek.FRIDAY, 17, "USA", "Denver", "Main103S");
        for(TrafficUnit tu: trafficUnits){
            Vehicle vehicle = FactoryVehicle.build(tu);
            vehicle.setSpeedModel(speedModel);
            double speed = vehicle.getSpeedMph(timeSec);
            speed = Math.round(speed * tu.getTraction());
            if(limitSpeed.test(tu, speed)){
                printResults.accept(tu, speed);
            }
        }
        System.out.println();

/*
        trafficUnits.stream()
                .map(tu -> {
                    Vehicle vehicle = FactoryVehicle.build(tu);
                    vehicle.setSpeedModel(speedModel);
                    return vehicle;
                })
                .map(v -> {
                    double speed = v.getSpeedMph(timeSec);
                    return Math.round(speed * tu.getTraction());
                })
                .filter(s -> limitSpeed.test(tu, s))
                .forEach(tuw -> printResults.accept(tu, s));
*/

        getTrafficUnitStream(trafficUnitsNumber)
                .map(TrafficUnitWrapper1::new)
                .map(tuw -> {
                    Vehicle vehicle = FactoryVehicle.build(tuw.getTrafficUnit());
                    vehicle.setSpeedModel(speedModel);
                    tuw.setVehicle(vehicle);
                    return tuw;
                })
                .map(tuw -> {
                    double speed = tuw.getVehicle().getSpeedMph(timeSec);
                    speed = Math.round(speed * tuw.getTrafficUnit().getTraction());
                    tuw.setSpeed(speed);
                    return tuw;
                })
                .filter(tuw -> limitSpeed.test(tuw.getTrafficUnit(), tuw.getSpeed()))
                .forEach(tuw -> printResults.accept(tuw.getTrafficUnit(), tuw.getSpeed()));

        System.out.println();

        getTrafficUnitStream(trafficUnitsNumber)
                .map(TrafficUnitWrapper2::new)
                .map(tuw -> tuw.setSpeedModel(speedModel))
                .map(tuw -> {
                    double speed = tuw.getVehicle().getSpeedMph(timeSec);
                    speed = Math.round(speed * tuw.getTrafficUnit().getTraction());
                    return tuw.setSpeed(speed);
                })
                .filter(tuw -> limitSpeed.test(tuw.getTrafficUnit(), tuw.getSpeed()))
                .forEach(tuw -> printResults.accept(tuw.getTrafficUnit(), tuw.getSpeed()));

        System.out.println();

        Predicate<TrafficUnit> limitTraffic = tu ->
                (tu.getHorsePower() < 250 && tu.getVehicleType() == Vehicle.VehicleType.CAR)
                        || (tu.getHorsePower() < 400 && tu.getVehicleType() == Vehicle.VehicleType.TRUCK);

        getTrafficUnitStream(trafficUnitsNumber)
                //.parallel()
                .filter(limitTraffic)
                .map(TrafficUnitWrapper3::new)
                .map(tuw -> tuw.setSpeedModel(speedModel))
                .map(tuw -> tuw.calcSpeed(timeSec))
                .filter(tuw -> limitSpeed.test(tuw.getTrafficUnit(), tuw.getSpeed()))
                .forEach(tuw -> printResults.accept(tuw.getTrafficUnit(), tuw.getSpeed()));

        System.out.println();
    }

    private static Stream<TrafficUnit> getTrafficUnitStream(int trafficUnitsNumber){
        return FactoryTraffic.getTrafficUnitStream(trafficUnitsNumber, Month.APRIL, DayOfWeek.FRIDAY, 17, "USA", "Denver", "Main103S");
    }

    private static class TrafficUnitWrapper1 {
        private double speed;
        private Vehicle vehicle;
        private TrafficUnit trafficUnit;
        public TrafficUnitWrapper1(TrafficUnit trafficUnit){
            this.trafficUnit = trafficUnit;
        }
        TrafficUnit getTrafficUnit(){
            return this.trafficUnit;
        }
        public Vehicle getVehicle() {
            return vehicle;
        }
        public void setVehicle(Vehicle vehicle) {
            this.vehicle = vehicle;
        }
        public double getSpeed() {
            return speed;
        }
        public void setSpeed(double speed) {
            this.speed = speed;
        }
    }

    private static class TrafficUnitWrapper2 {
        private double speed;
        private Vehicle vehicle;
        private TrafficUnit trafficUnit;
        public TrafficUnitWrapper2(TrafficUnit trafficUnit){
            this.trafficUnit = trafficUnit;
            this.vehicle = FactoryVehicle.build(trafficUnit);
        }
        public TrafficUnitWrapper2 setSpeedModel(SpeedModel speedModel) {
            this.vehicle.setSpeedModel(speedModel);
            return this;
        }
        TrafficUnit getTrafficUnit(){
            return this.trafficUnit;
        }
        public Vehicle getVehicle() { return vehicle; }
        public double getSpeed() {
            return speed;
        }
        public TrafficUnitWrapper2 setSpeed(double speed) {
            this.speed = speed;
            return this;
        }
    }

    private static class TrafficUnitWrapper3 {
        private double speed;
        private Vehicle vehicle;
        private TrafficUnit trafficUnit;
        public TrafficUnitWrapper3(TrafficUnit trafficUnit){
            this.trafficUnit = trafficUnit;
            this.vehicle = FactoryVehicle.build(trafficUnit);
        }
        public TrafficUnitWrapper3 setSpeedModel(SpeedModel speedModel) {
            this.vehicle.setSpeedModel(speedModel);
            return this;
        }
        TrafficUnit getTrafficUnit(){
            return this.trafficUnit;
        }
        public double getSpeed() {
            return speed;
        }
        public TrafficUnitWrapper3 calcSpeed(double timeSec) {
            double speed = this.vehicle.getSpeedMph(timeSec);
            this.speed = Math.round(speed * this.trafficUnit.getTraction());
            return this;
        }
    }

    public interface Traffic {
        void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel,
                             BiPredicate<TrafficUnit,Double> limitTraffic, BiConsumer<TrafficUnit, Double> printResult);
    }

    private static class TrafficImpl implements Traffic {
        private int hour;
        private Month month;
        private DayOfWeek dayOfWeek;
        private String country, city, trafficLight;

        public TrafficImpl(Month month, DayOfWeek dayOfWeek, int hour, String country, String city, String trafficLight){
            this.month = month;
            this.dayOfWeek = dayOfWeek;
            this.hour = hour;
            this.country = country;
            this.city = city;
            this.trafficLight = trafficLight;
        }

        public void speedAfterStart(double timeSec, int trafficUnitsNumber, SpeedModel speedModel,
                                    BiPredicate<TrafficUnit, Double> limitSpeed, BiConsumer<TrafficUnit, Double> printResult) {
            List<TrafficUnit> trafficUnits = FactoryTraffic.generateTraffic(trafficUnitsNumber,
                    month, dayOfWeek, hour, country, city, trafficLight);
            for(TrafficUnit tu: trafficUnits){
                Vehicle vehicle = FactoryVehicle.build(tu);
                vehicle.setSpeedModel(speedModel);
                double speed = vehicle.getSpeedMph(timeSec);
                speed = Math.round(speed * tu.getTraction());
                if(limitSpeed.test(tu, speed)){
                    printResult.accept(tu, speed);
                }
            }
        }
    }

    public static void demo5_parallel() {

        System.out.println();
        List.of("This ", "is ", "created ", "by ", "List.of().stream()")
                .stream().forEach(System.out::print);
        System.out.println();

        System.out.println();
        List.of("This ", "is ", "created ", "by ", "List.of().parallelStream()")
                .parallelStream().forEach(System.out::print);
        System.out.println();

        System.out.println();
        List<String> wordsWithI = new ArrayList<>();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .parallel()
                .filter(w -> w.contains("i"))
                .forEach(wordsWithI::add);
        System.out.println(wordsWithI);

        System.out.println();

        wordsWithI = Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .parallel()
                .filter(w -> w.contains("i"))
                .collect(Collectors.toList());
        System.out.println(wordsWithI);
    }

    public static void filterStream(){
        int sum = Stream.of( 1,2,3,4,5,6,7,8,9 )
                .filter(i -> i % 2 != 0)
                .peek(i -> System.out.print(i))
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("\nsum = " + sum);

        System.out.println();
        Stream.of( "That ", "is ", "a ", "Stream.of(literals)" )
                .filter(s -> s.contains("i"))
                .forEach(System.out::print);


    }

}


