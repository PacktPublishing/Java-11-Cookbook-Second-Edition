package com.packt.cookbook.ch07_concurrency;

import java.util.concurrent.Flow;

public class DemoSubscriber<T> implements Flow.Subscriber<T> {
    private String name;
    private Flow.Subscription subscription;

    public DemoSubscriber(String name){
        this.name = name;
    }

    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(0);
    }

    public void onNext(T item) {
        System.out.println(name + " received: " + item);
        this.subscription.request(1);
    }

    public void onError(Throwable ex) {
        ex.printStackTrace();
    }

    public void onComplete() {
        System.out.println("Completed");
    }
}
