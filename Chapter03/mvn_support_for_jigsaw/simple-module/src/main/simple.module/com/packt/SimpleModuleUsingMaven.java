package com.packt;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import com.packt.util.SimpleModularUtil;
public class SimpleModuleUsingMaven{
    
    public static void main(String[] args) throws Exception{
        SimpleModularUtil util = new SimpleModularUtil();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        data.put("msg", util.message());
        System.out.println(objectMapper.writeValueAsString(data));
    }
}