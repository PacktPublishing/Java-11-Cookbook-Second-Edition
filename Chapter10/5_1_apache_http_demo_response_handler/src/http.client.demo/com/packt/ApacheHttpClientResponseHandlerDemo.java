package com.packt;

import java.io.IOException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClientResponseHandlerDemo{
    public static void main(String [] args) throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            HttpGet request = new HttpGet("http://httpbin.org/get");

            String responseBody = client.execute(request, response -> {
                int status = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
            System.out.println("Response Body: " + responseBody);
        }finally{
            client.close();
        }
    }
}