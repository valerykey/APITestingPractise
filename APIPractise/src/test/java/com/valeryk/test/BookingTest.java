package com.valeryk.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeryk.Base;
import com.valeryk.HttpWrapper;
import com.valeryk.ObjectMapperWrapper;
import com.valeryk.dataprovider.Data;
import com.valeryk.methodchaining.NewHttpWrapper;
import com.valeryk.valueobjects.response.BookingDates;
import com.valeryk.valueobjects.response.BookingID;
import com.valeryk.valueobjects.response.NewBooking;
import org.apache.http.entity.StringEntity;
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

    final static String CONTENT = "Content-Type";
    final static String FORMAT = "application/json";
    final static String ACCEPT = "Accept";

    @Test
    public void getBookingIDTest() throws IOException {
        //get all booking ids
        List<BookingID> listBookingId = new ObjectMapper().readValue(
                NewHttpWrapper.prepare().init().
                        get("/booking").
                        execute(),
                new TypeReference<>(){});
        assertThat(listBookingId.get(0).getBookingid(), notNullValue());

    }

    @Test
    public void getBookingTest() throws IOException {
        //get booking from id
        Booking booking = ObjectMapperWrapper.deserialize(
                Booking.class,
                NewHttpWrapper.prepare().init().
                        get("/booking/1").
                        header(ACCEPT, FORMAT).
                        execute());
        assertThat(booking.getFirstname(),notNullValue());
        assertThat(booking.getBookingdates(),notNullValue());
    }

    @Test
    public void updateBookingTest() throws IOException {
        //update booking
        Booking booking = Data.createBookingObject("Jim", "Brown", 111, true, new BookingDates("2018-01-01", "2019-01-01"), "breakfast");
        Booking newBooking = ObjectMapperWrapper.deserialize(Booking.class,
                NewHttpWrapper.prepare().init().
                put("/booking/1", ObjectMapperWrapper.serialize(booking)).
                header(ACCEPT, FORMAT).header(CONTENT, FORMAT).
                header("cookie", "token=" + HttpWrapper.generateToken().getToken()).
                execute());
        assertThat(newBooking.getFirstname(),equalTo("Jim"));

        /*NewBooking booking = HttpWrapper.createBooking();
        booking.getBooking().setFirstname("John");
        HttpPut httpPut = HttpWrapper.put(BOOKING_URL + "/" + String.valueOf(booking.getBookingid()), HttpWrapper.generateToken().getToken());
        httpPut.setEntity(ObjectMapperWrapper.serialize(booking.getBooking()));
        Booking new_booking = ObjectMapperWrapper.deserialize(Booking.class, httpclient.execute(httpPut, getResponse()));
        assertThat(new_booking.getFirstname(),equalTo("John"));*/
    }

    @Test
    public void createBookingTest() throws IOException {
       // NewBooking booking = HttpWrapper.createBooking();
        Booking booking = Data.createBookingObject("Jim", "Brown", 111, true, new BookingDates("2018-01-01", "2019-01-01"), "breakfast");
        NewBooking newBooking = ObjectMapperWrapper.deserialize(NewBooking.class,
                NewHttpWrapper.prepare().init().
                post("/booking", ObjectMapperWrapper.serialize(booking)).
                header(ACCEPT, FORMAT).header(CONTENT, FORMAT).
                execute());
        assertThat(newBooking.getBookingid(),notNullValue());
    }

    @Test
    public void partialUpdateTest() throws IOException {
        JSONObject json = new JSONObject();
        json.put("firstname", "James");
        json.put("lastname", "Brown");
        Booking booking = ObjectMapperWrapper.deserialize(Booking.class, NewHttpWrapper.prepare().init().
                patch("/booking/4", new StringEntity(json.toString())).
                header(ACCEPT, FORMAT).header(CONTENT, FORMAT).
                header("cookie", "token=" + HttpWrapper.generateToken().getToken()).
                execute());
        assertThat(booking.getFirstname(),equalTo("James"));
        assertThat(booking.getLastname(),equalTo("Brown"));
    }

    @Test
    public void deleteTest() throws IOException {

        assertThat(NewHttpWrapper.prepare().init().delete("/booking/3").
                header(CONTENT, FORMAT).
                header("cookie", "token=" + HttpWrapper.generateToken().getToken()).
                execute(), equalTo("Created"));
    }

    @Test
    public void pingTest() throws IOException {
        assertThat(NewHttpWrapper.prepare().init().get("/ping").execute(), equalTo("Created"));
    }

}
