package com.packt;

import java.net.http.*;
import java.net.URI;
public class HttpRequestProtectedResDemo{
    public static void main(String [] args) throws Exception{
        //username and password can be hard coded or taken from user input
        String username = "user";
        String password = "passwd";   
        UsernamePasswordAuthenticator authenticator = 
            new UsernamePasswordAuthenticator(username, password);

        HttpClient client = HttpClient
            .newBuilder()
            .authenticator(authenticator)
            .build();

        HttpRequest request = HttpRequest
            .newBuilder(new URI("http://httpbin.org/basic-auth/user/passwd"))
            .GET()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

    }
}