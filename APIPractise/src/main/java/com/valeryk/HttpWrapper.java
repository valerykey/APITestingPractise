package com.valeryk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeryk.dataprovider.Data;
import com.valeryk.valueobjects.response.*;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.IOException;
import java.util.List;

public class HttpWrapper {

    final static String TOKEN_URL = "https://restful-booker.herokuapp.com/auth";
    final static String HEADER_CONTENT = "Content-Type";
    final static String HEADER_FORMAT = "application/json";
    final static String HEADER_ACCEPT = "Accept";
    final static String TOKEN_USERNAME = "admin";
    final static String TOKEN_PASSWORD = "password123";
    final static String BOOKING_URL = "https://restful-booker.herokuapp.com/booking";

    static CloseableHttpClient  httpclient = HttpClients.createDefault();

    public static HttpPost post(String url, String content) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(content, HEADER_FORMAT);
        return httpPost;
    }

    public static HttpPost post(String url, String content, String accept) {
        HttpPost httpPost = post(url,content);
        httpPost.setHeader(accept, HEADER_FORMAT);
        return httpPost;
    }
    public static HttpGet get(String url) {
        return new HttpGet(url);
    }

    public static HttpGet get(String url, String header_field) {
        HttpGet httpGet = get(url);
        httpGet.setHeader(header_field, HEADER_FORMAT);
        return  httpGet;
    }

    public static HttpPut put(String url, String token) {
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader(HEADER_CONTENT, HEADER_FORMAT);
        httpPut.setHeader(HEADER_ACCEPT, HEADER_FORMAT);
        httpPut.setHeader("Cookie", "token="+token);
        return httpPut;
    }

    public static HttpPatch patch(String url, String token) {
        HttpPatch httpPatch = new HttpPatch(url);
        httpPatch.setHeader(HEADER_CONTENT, HEADER_FORMAT);
        httpPatch.setHeader(HEADER_ACCEPT, HEADER_FORMAT);
        httpPatch.setHeader("Cookie", "token="+token);
        return httpPatch;
    }

    public static HttpDelete delete(String url, String token) {
        HttpDelete httpDelete = new HttpDelete(url);
        httpDelete.setHeader(HEADER_CONTENT, HEADER_FORMAT);
        httpDelete.setHeader("Cookie", "token="+token);
        return httpDelete;
    }



    public static Token generateToken() throws IOException {
        HttpPost httpPost = HttpWrapper.post(TOKEN_URL, HEADER_CONTENT);
        httpPost.setEntity(ObjectMapperWrapper.serialize(Data.createAuthentificationObject(TOKEN_USERNAME, TOKEN_PASSWORD)));
        ResponseHandler<String> responseHandler = Base.getResponse();
        return ObjectMapperWrapper.deserialize(Token.class, httpclient.execute(httpPost, responseHandler));
    }

    public static List<BookingID> getListId() throws IOException {
        HttpGet httpGet = HttpWrapper.get(BOOKING_URL);
        String responseBody = httpclient.execute(httpGet, Base.getResponse());
        return new ObjectMapper().readValue(responseBody, new TypeReference<>(){});
    }

    public static Booking getBooking(int id) throws IOException {
        HttpGet httpGet = HttpWrapper.get(BOOKING_URL + "/" + id,HEADER_ACCEPT);
        return ObjectMapperWrapper.deserialize(Booking.class, httpclient.execute(httpGet, Base.getResponse()));
    }

    public static NewBooking createBooking() throws IOException {
        HttpPost httpPost = HttpWrapper.post(BOOKING_URL,HEADER_ACCEPT,HEADER_CONTENT);
        Booking booking = Data.createBookingObject("Jim", "Brown", 111, true, new BookingDates("2018-01-01", "2019-01-01"), "breakfast");
        httpPost.setEntity(ObjectMapperWrapper.serialize(booking));
        return ObjectMapperWrapper.deserialize(NewBooking.class, httpclient.execute(httpPost, Base.getResponse()));
    }


}
