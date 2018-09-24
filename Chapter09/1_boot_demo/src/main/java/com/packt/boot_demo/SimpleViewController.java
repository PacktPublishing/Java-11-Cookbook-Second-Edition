package com.packt.boot_demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleViewController{

    @GetMapping("/message")
    public String message(){
        return "message";
    }
}