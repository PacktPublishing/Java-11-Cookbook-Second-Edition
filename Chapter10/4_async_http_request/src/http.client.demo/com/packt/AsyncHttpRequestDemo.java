package com.packt;

import java.net.http.*;
import java.net.URI;
import java.util.concurrent.CompletableFuture;
public class AsyncHttpRequestDemo{
    public static void main(String [] args) throws Exception{
        HttpClient client = HttpClient
            .newBuilder()
            .build();

        HttpRequest request = HttpRequest
            .newBuilder(new URI("http://httpbin.org/get"))
            .GET()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

        CompletableFuture<HttpResponse<String>> responseFuture = 
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        
        CompletableFuture<Void> processedFuture = 
            responseFuture.thenAccept(response -> {
                System.out.println("Status code: " + response.statusCode());
                System.out.println("Response Body: " + response.body());
            });

        //wait for the CompleteableFuture to complete
        CompletableFuture.allOf(processedFuture).join();
    }
}