package com.kafka;
public class B extends A{
    static {
        System.out.println("a");
    }
    
    public B(){
        System.out.println("b");
    }
}
