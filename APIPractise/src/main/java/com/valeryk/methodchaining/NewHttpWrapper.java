package com.valeryk.methodchaining;

import com.fasterxml.jackson.core.type.TypeReference;
import com.valeryk.Base;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class NewHttpWrapper {

    final static String HEADER_FORMAT = "application/json";
    final static String HEADER_ACCEPT = "Accept";
    final static String BASE_URL = "https://restful-booker.herokuapp.com";

    private CloseableHttpClient httpClient;
    private String url;
    private String uri;
    private HttpGet httpGet;
    private String responseBody;
    private HttpUriRequest request;

    /*HttpClient client = HttpClients.custom().build();
    HttpUriRequest request = RequestBuilder.get()
            .setUri(SAMPLE_URL)
            .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
client.execute(request);*/

    public static NewHttpWrapper prepare(){
        return new NewHttpWrapper();
    }
    public NewHttpWrapper init(){
       httpClient =  HttpClients.createDefault();
       return this;
    }

    public NewHttpWrapper get(String path)  {
        request = RequestBuilder.get().setUri(BASE_URL + path).build();
        return this;
    }

    public NewHttpWrapper post(String path, StringEntity entity){
        request = RequestBuilder.post().setUri(BASE_URL + path).setEntity(entity).build();
        return this;
    }

    public NewHttpWrapper put(String path, StringEntity entity){
        request = RequestBuilder.put().setUri(BASE_URL + path).setEntity(entity).build();
        return this;
    }

    public NewHttpWrapper patch(String path, StringEntity entity){
        request = RequestBuilder.patch().setUri(BASE_URL + path).setEntity(entity).build();
        return this;
    }

    public NewHttpWrapper delete(String path){
        request = RequestBuilder.delete().setUri(BASE_URL + path).build();
        return this;
    }

    public NewHttpWrapper header(String type, String value){
        request.setHeader(type, value);
        return this;
    }


    public String execute() throws IOException {
       return httpClient.execute(request, Base.getResponse());
    }

    /*public NewHttpWrapper post(T t)

    public void getResponse(String responseBody){

       // String responseBody = httpclient.execute(httpGet, Base.getResponse());
        //return null;
    }*/
}
