package com.kafka;

import java.util.LinkedList;
import java.util.List;

public class C {
    public static void main(String[] args) {
//        @SuppressWarnings("unused")
//        A a=new B();
//        a=new B();
        
        
        List<String> li=new LinkedList<>();
        li.add("123");
        
        for (String str : li) {
            System.out.println(str);
        }
        
    }
}
