package com.gongxc.impl;


import com.gongxc.inter.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {

    public String sayHi(String name) {
        return "hi, " + name;
    }

    public String sayHello() {
        return "hello";
    }
}
