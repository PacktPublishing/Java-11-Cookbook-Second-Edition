package com.packt.cookbook.ch05_streams;

public class Thing{
    private int someInt;

    public Thing(int i){
        this.someInt = i;
    }

    public int getSomeInt() {
        return someInt;
    }

    public String getSomeStr() {
        return Integer.toString(someInt);
    }


}
