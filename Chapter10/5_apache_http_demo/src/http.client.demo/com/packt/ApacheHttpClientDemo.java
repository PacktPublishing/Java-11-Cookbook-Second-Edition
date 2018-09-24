package com.packt;

import java.io.IOException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClientDemo{
    public static void main(String [] args) throws Exception{
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            HttpGet request = new HttpGet("http://httpbin.org/get");

            CloseableHttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println("Status code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
        }finally{
            client.close();
        }
    }

}