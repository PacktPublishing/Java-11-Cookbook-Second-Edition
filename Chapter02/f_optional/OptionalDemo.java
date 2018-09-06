package com.packt.cookbook.ch02_oop.f_optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class OptionalDemo {
    public static void main(String... args){
        //create();
        //compare();

        //checkResultInt(null);
        //checkResultInt(0);
        //checkResultInt(1000);

        //checkResultOpt(null);
        //checkResultOpt(Optional.ofNullable(null));
        //checkResultOpt(Optional.of(0));
        //checkResultOpt(Optional.of(1000));

        //or();
        //ifPresent();
        //ifPresentOrElse();
        //orElseGet1();
        //orElseGet2();
        //orElseThrow1();
        //orElseThrow2();

        List<Optional<Integer>> list =
                List.of(Optional.empty(), Optional.ofNullable(null), Optional.of(100000));
        //useFilter2(list);
        //useMap2(list);
        //useFlatMap1(list);
        useFlatMap3(list);
    }

    private static void useFlatMap3(List<Optional<Integer>> list){
        Function<Optional<Integer>, Stream<Optional<Integer>>> tryUntilWin = opt -> {
            List<Optional<Integer>> opts = new ArrayList<>();
            if(opt.isPresent()){
                opts.add(opt);
            } else {
                int prize = 0;
                while(prize == 0){
                    double d = Math.random() - 0.8;
                    prize = d > 0 ? (int)(1000000 * d) : 0;
                    opts.add(Optional.of(prize));
                }
            }
            return opts.stream();
        };
        list.stream().flatMap(tryUntilWin).forEach(weWonOpt());
    }

    private static void useFlatMap2(List<Optional<Integer>> list){
        Function<Optional<Integer>, Stream<Optional<Integer>>> tryUntilWin = opt -> {
            List<Optional<Integer>> opts = new ArrayList<>();
            if(opt.isPresent()){
                opts.add(opt);
            } else {
                int prize = 0;
                while(prize == 0){
                    double d = Math.random() - 0.8;
                    prize = d > 0 ? (int)(1000000 * d) : 0;
                    opts.add(Optional.of(prize));
                }
            }
            return opts.stream();
        };
        list.stream().flatMap(tryUntilWin).forEach(opt -> checkResultAndShare(opt.get()));
    }

    private static void useFlatMap1(List<Optional<Integer>> list){
        list.stream().flatMap(opt -> List.of(opt.or(()->Optional.of(0))).stream())
                .forEach(weWonOpt());
    }

    private static void useFlatMap0(List<Optional<Integer>> list){
        list.stream().flatMap(opt -> List.of(opt.or(()->Optional.of(0))).stream())
                .forEach(opt -> checkResultAndShare(opt.get()));
    }

    private static void useMap2(List<Optional<Integer>> list){
        list.stream().map(opt -> opt.or(() -> Optional.of(0)))
                .forEach(weWonOpt());
    }

    private static void useMap1(List<Optional<Integer>> list){
        list.stream().map(opt -> opt.or(() -> Optional.of(0)))
                .forEach(opt -> checkResultAndShare(opt.get()));
    }

    private static void useFilter2(List<Optional<Integer>> list){
        list.stream().filter(opt -> opt.isPresent())
                .forEach(weWonOpt());
    }
    private static void useFilter1(List<Optional<Integer>> list){
        list.stream().filter(opt -> opt.isPresent())
                .forEach(opt -> checkResultAndShare(opt.get()));
    }
    private static Consumer<Optional<Integer>> weWonOpt(){
        return opt -> checkResultAndShare(opt.get());
    }

    private static void orElseThrow2(){
        try{
            processOrThrow2(Optional.empty());
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        try{
            processOrThrow2(Optional.ofNullable(null));
        } catch (RuntimeException ex){
            System.out.println(ex.getMessage());
        }
        processOrThrow2(Optional.of(100000));

    }
    private static void processOrThrow2(Optional<Integer> lotteryPrize){
        int prize = lotteryPrize.orElseThrow(() -> new RuntimeException("We've lost again..."));
        checkResultAndShare(prize);
    }

    private static void orElseThrow1(){
        try{
            processOrThrow1(Optional.empty());
        } catch (RuntimeException ex){
            System.out.println("We've lost again...");
        }
        try{
            processOrThrow1(Optional.ofNullable(null));
        } catch (RuntimeException ex){
            System.out.println("We've lost again...");
        }
        processOrThrow1(Optional.of(100000));
    }
    private static void processOrThrow1(Optional<Integer> lotteryPrize){
        int prize = lotteryPrize.orElseThrow();
        checkResultAndShare(prize);
    }

    private static void orElseGet2(){
        processOrGet2(Optional.empty());
        processOrGet2(Optional.ofNullable(null));
        processOrGet2(Optional.of(0));
        processOrGet2(Optional.of(100000));

    }
    private static void processOrGet2(Optional<Integer> lotteryPrize){
        lotteryPrize.ifPresentOrElse(i -> System.out.println(""),
                () -> System.out.println("\nWe've lost again..."));

        Supplier<Integer> tryUntilWin = () -> {
            int prize = 0;
            while(prize == 0){
                double d = Math.random() - 0.8;
                prize = d > 0 ? (int)(1000000 * d) : 0;
                System.out.println("We've lost again...");
            }
            return prize;
        };
        int prize = lotteryPrize.orElseGet(tryUntilWin);
        lotteryPrize.ifPresentOrElse(weWon(), better(prize));
    }

    private static void orElseGet1(){
        processOrGet1(Optional.empty());
        processOrGet1(Optional.ofNullable(null));
        processOrGet1(Optional.of(0));
        processOrGet1(Optional.of(100000));

    }
    private static void processOrGet1(Optional<Integer> lotteryPrize){
        int prize = lotteryPrize.orElseGet(() -> 42);
        lotteryPrize.ifPresentOrElse(weWon(), better(prize));
    }
    private static Runnable better(int prize){
        return () -> System.out.println("Better " + prize + " than nothing...");
    }
    private static void processOrGet(Optional<Integer> lotteryPrize){
        int prize = lotteryPrize.orElseGet(() -> 42);
        lotteryPrize.ifPresentOrElse(p -> checkResultAndShare(p),
                () -> System.out.println("Better " + prize + " than nothing..."));
    }

    private static void ifPresentOrElse(){
        processIfPresentOrElse2(Optional.empty());
        processIfPresentOrElse2(Optional.ofNullable(null));
        processIfPresentOrElse2(Optional.of(0));
        processIfPresentOrElse2(Optional.of(100000));
    }

    private static void processIfPresentOrElse2(Optional<Integer> lotteryPrize){
       lotteryPrize.ifPresentOrElse(weWon(), weLost());
    }
    private static Consumer<Integer> weWon(){
        return prize -> checkResultAndShare(prize);
    }
    private static Runnable weLost(){
        return () -> System.out.println("We've lost again...");
    }


    private static void processIfPresentOrElse1(Optional<Integer> lotteryPrize){
        Consumer<Integer> weWon = prize -> checkResultAndShare(prize);
        Runnable weLost = () -> System.out.println("We've lost again...");
        lotteryPrize.ifPresentOrElse(weWon, weLost);
    }

    private static void ifPresent(){
        processIfPresent2(Optional.empty());
        processIfPresent2(Optional.ofNullable(null));
        processIfPresent2(Optional.of(0));
        processIfPresent2(Optional.of(100000));

    }
    private static void processIfPresent2(Optional<Integer> lotteryPrize){
        lotteryPrize.ifPresent(prize -> checkResultAndShare(prize));
    }
    private static void processIfPresent1(Optional<Integer> lotteryPrize){
        lotteryPrize.ifPresent(prize -> {
            if(prize <= 0){
                System.out.println("We've lost again...");
            } else {
                System.out.println("We've won! Your half is " + Math.round(((double)prize)/2) + "!");
            }

        });
    }

    private static void or(){
        or2(Optional.empty());
        or2(Optional.ofNullable(null));
        or2(Optional.of(0));
        or2(Optional.of(100000));

    }
    private static void or2(Optional<Integer> lotteryPrize){
        int prize = lotteryPrize.or(() -> Optional.of(0)).get();
        checkResultAndShare(prize);
    }

    private static void or1(Optional<Integer> lotteryPrize){
        int prize = lotteryPrize.or(() -> Optional.of(0)).get();
        if(prize <= 0){
            System.out.println("We've lost again...");
        } else {
            System.out.println("We've won! Your half is " + Math.round(((double)prize)/2) + "!");
        }
    }

    private static void checkResultAndShare(int prize){
        if(prize <= 0){
            System.out.println("We've lost again...");
        } else {
            System.out.println("We've won! Your half is " + Math.round(((double)prize)/2) + "!");
        }
    }

    private static void checkResultOpt(Optional<Integer> lotteryPrize){
        if(lotteryPrize == null || !lotteryPrize.isPresent() || lotteryPrize.get() <= 0){
            System.out.println("We've lost again..." + lotteryPrize);
        } else {
            System.out.println("We've won! Your half is " + Math.round(((double)lotteryPrize.get())/2) + "!");
        }
    }

    private static void checkResultInt(Integer lotteryPrize){
        if(lotteryPrize == null || lotteryPrize <= 0){
            System.out.println("We've lost again...");
        } else {
            System.out.println("We've won! Your half is " + Math.round(((double)lotteryPrize)/2) + "!");
        }
    }

    private static void compare(){
        Optional<Integer> prize1 = Optional.empty();
        System.out.println(prize1.equals(prize1));    //prints: true

        Optional<Integer> prize2 = Optional.of(1000000);
        System.out.println(prize1.equals(prize2));    //prints: false

        Optional<Integer> prize3 = Optional.ofNullable(null);
        System.out.println(prize1.equals(prize3));    //prints: true

        Optional<Integer> prize4 = Optional.of(1000000);
        System.out.println(prize2.equals(prize4));    //prints: true
        System.out.println(prize2 == prize4);         //prints: false

        Optional<Integer> prize5 = Optional.of(10);
        System.out.println(prize2.equals(prize5));    //prints: false

        Optional<String> congrats1 = Optional.empty();
        System.out.println(prize1.equals(congrats1)); //prints: true

        Optional<String> congrats2 = Optional.of("Happy for you!");
        System.out.println(prize1.equals(congrats2)); //prints: false

    }


    private static void create(){
        Optional<Integer> prize1 = Optional.empty();
        System.out.println(prize1.isPresent());       //prints: false
        System.out.println(prize1);                   //prints: Optional.empty

        Optional<Integer> prize2 = Optional.of(1000000);
        System.out.println(prize2.isPresent());       //prints: true
        System.out.println(prize2);                   //prints: Optional[1000000]

        //Optional<Integer> prize = Optional.of(null);  //NullPointerException

        Optional<Integer> prize3 = Optional.ofNullable(null);
        System.out.println(prize3.isPresent());       //prints: false
        System.out.println(prize3);                   //prints: Optional.empty

    }

}
