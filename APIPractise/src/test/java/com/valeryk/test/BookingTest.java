package com.valeryk.test;

import com.valeryk.Base;
import com.valeryk.HttpWrapper;
import com.valeryk.ObjectMapperWrapper;
import com.valeryk.dataprovider.Data;
import com.valeryk.valueobjects.response.BookingDates;
import com.valeryk.valueobjects.response.BookingID;
import com.valeryk.valueobjects.response.NewBooking;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import com.valeryk.valueobjects.response.Booking;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingTest extends Base {

    final String BOOKING_URL = "https://restful-booker.herokuapp.com/booking";

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void getBookingIDTest() throws IOException {
        //get all booking ids
        List<BookingID> listBookingId= HttpWrapper.getListId();
        assertThat(listBookingId.get(0).getBookingid(), notNullValue());
        var response = HttpWrapper.prepare().getX("/uri").execute().getREsponse();
        var response2 = HttpWrapper.prepare().post(null).execute().getREsponse();
    }

    @Test
    public void getBookingTest() throws IOException {
        //get booking from id
        Booking booking = HttpWrapper.getBooking(HttpWrapper.getListId().get(0).getBookingid());
        assertThat(booking.getFirstname(),notNullValue());
        assertThat(booking.getBookingdates(),notNullValue());
    }

    @Test
    public void updateBookingTest() throws IOException {
        //update booking
        NewBooking booking = HttpWrapper.createBooking();
        booking.getBooking().setFirstname("John");
        HttpPut httpPut = HttpWrapper.put(BOOKING_URL + "/" + String.valueOf(booking.getBookingid()), HttpWrapper.generateToken().getToken());
        httpPut.setEntity(ObjectMapperWrapper.serialize(booking.getBooking()));
        Booking new_booking = ObjectMapperWrapper.deserialize(Booking.class, httpclient.execute(httpPut, getResponse()));
        assertThat(new_booking.getFirstname(),equalTo("John"));
    }

    @Test
    public void createBookingTest() throws IOException {
        NewBooking booking = HttpWrapper.createBooking();
        assertThat(booking.getBookingid(),notNullValue());
    }

    @Test
    public void deleteTest() throws IOException {
        HttpDelete httpDelete = HttpWrapper.delete(BOOKING_URL + "/" + HttpWrapper.getListId().get(0).getBookingid(), HttpWrapper.generateToken().getToken());
        assertThat(httpclient.execute(httpDelete, getResponse()), equalTo("Created"));
    }

    @Test
    public void partialUpdateTest() throws IOException {
        HttpPatch httpPatch = HttpWrapper.patch(BOOKING_URL + "/" + HttpWrapper.getListId().get(0).getBookingid(), HttpWrapper.generateToken().getToken());
        JSONObject json = new JSONObject();
        json.put("firstname", "James");
        json.put("lastname", "Brown");
        httpPatch.setEntity(new StringEntity(json.toString()));
        Booking booking = ObjectMapperWrapper.deserialize(Booking.class, httpclient.execute(httpPatch, getResponse()));
       // assertThat(booking.getFirstname(),equalTo("Helen"));
    }

    @Test
    public void pingTest() throws IOException {
        assertThat(httpclient.execute(HttpWrapper.get("https://restful-booker.herokuapp.com/ping"), getResponse()), equalTo("Created"));
    }

}
