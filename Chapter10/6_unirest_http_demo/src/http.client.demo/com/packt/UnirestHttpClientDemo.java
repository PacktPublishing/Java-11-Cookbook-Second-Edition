package com.packt;

import com.mashape.unirest.request.body.*;
import com.mashape.unirest.request.*;
import com.mashape.unirest.http.*;
public class UnirestHttpClientDemo{
    public static void main(String [] args) throws Exception{
        HttpResponse<JsonNode> jsonResponse = Unirest.get("http://httpbin.org/get")
            .asJson();

        int statusCode = jsonResponse.getStatus();
        JsonNode jsonBody = jsonResponse.getBody();
        System.out.println("Response Status: " + statusCode);
        System.out.println("Response Body: ");
        System.out.println(jsonBody);

        jsonResponse = Unirest.post("http://httpbin.org/post")
            .field("key1", "val1")
            .field("key2", "val2")
            .asJson();

        System.out.println("Response Body: ");
        System.out.println(jsonResponse.getBody());

        jsonResponse = Unirest.get("http://httpbin.org/basic-auth/user/passwd")
            .basicAuth("user", "passwd")
            .asJson();

        System.out.println("Response Body: ");
        System.out.println(jsonResponse.getBody());

    }

}