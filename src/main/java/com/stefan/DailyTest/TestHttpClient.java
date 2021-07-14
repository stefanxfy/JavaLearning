package com.stefan.DailyTest;

import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class TestHttpClient {
    public static void main(String[] args) throws IOException {
        //Creating an HttpRequestInterceptor
        HttpRequestInterceptor requestInterceptor = new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest request, HttpContext context) throws
                    HttpException, IOException {
                if(request.containsHeader("sample-header")){
                    System.out.println("Contains header sample-header, removing it..");
                    request.removeHeaders("sample-header");
                }
                //Printing remaining list of headers
                Header[] headers= request.getAllHeaders();
                for (int i = 0; i<headers.length;i++){
                    System.out.println(headers[i].getName());
                }
            }
        };
        //Creating a CloseableHttpClient object
        CloseableHttpClient httpclient =
                HttpClients.custom().addInterceptorFirst(requestInterceptor).build();

        //Creating a request object
        HttpGet httpget1 = new HttpGet("https://www.kaops.com/");

        //Setting the header to it
        httpget1.setHeader(new BasicHeader("sample-header","My first header"));
        httpget1.setHeader(new BasicHeader("demo-header","My second header"));
        httpget1.setHeader(new BasicHeader("test-header","My third header"));

        //Executing the request
        HttpResponse httpresponse = httpclient.execute(httpget1);
        System.out.println(httpresponse.getStatusLine());

    }
}
