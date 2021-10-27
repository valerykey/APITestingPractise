package com.valeryk.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valeryk.Base;
import com.valeryk.HttpWrapper;
import com.valeryk.ObjectMapperWrapper;
import com.valeryk.config.ConfigMap;
import com.valeryk.config.JSONReader;
import com.valeryk.dataprovider.Data;
import com.valeryk.methodchaining.NewHttpWrapper;
import com.valeryk.valueobjects.response.BookingDates;
import com.valeryk.valueobjects.response.BookingID;
import com.valeryk.valueobjects.response.NewBooking;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.valeryk.valueobjects.response.Booking;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingTest extends Base {

    final static String CONTENT = "Content-Type";
    final static String FORMAT = "application/json";
    final static String ACCEPT = "Accept";
    ConfigMap configMap;

    private static final Logger LOG = LogManager.getLogger(BookingTest.class);

    @Tag(value = "booking")
    @Test
    public void getBookingIDTest() throws IOException {
        //get all booking ids
        LOG.info("Get booking Id test");
        List<BookingID> listBookingId = new ObjectMapper().readValue(
                NewHttpWrapper.prepare().init().
                        get("/booking").
                        execute(),
                new TypeReference<>(){});
        assertThat(listBookingId.get(0).getBookingid(), notNullValue());

    }

    @Tag(value = "booking")
    @Test
    public void getBookingTest() throws IOException {
        //get booking from id
        LOG.info("Get booking Id test");
        Booking booking = ObjectMapperWrapper.deserialize(
                Booking.class,
                NewHttpWrapper.prepare().init().
                        get("/booking/1").
                        header(ACCEPT, FORMAT).
                        execute());
        assertThat(booking.getFirstname(),notNullValue());
        assertThat(booking.getBookingdates(),notNullValue());
    }

    @Tag(value = "booking")
    @Test
    public void updateBookingTest() throws IOException {
        //update booking
        LOG.info("Update booking test");
        Booking booking= new ObjectMapper().readValue(new File("src/main/resources/booking.json"), Booking.class);
        configMap = JSONReader.loadJSON("src/main/resources/booking.json");
        System.out.println(configMap.get(0));
        Booking newBooking = ObjectMapperWrapper.deserialize(Booking.class,
                NewHttpWrapper.prepare().init().
                put("/booking/1", ObjectMapperWrapper.serialize(booking)).
                header(ACCEPT, FORMAT).header(CONTENT, FORMAT).
                header("cookie", "token=" + HttpWrapper.generateToken().getToken()).
                execute());
        assertThat(newBooking.getFirstname(),equalTo("Jim"));
    }

    @Tag(value = "booking")
    @Test
    public void createBookingTest() throws IOException {
        LOG.info("Get booking Id test");
        Booking booking= new ObjectMapper().readValue(new File("src/main/resources/booking.json"), Booking.class);
        NewBooking newBooking = ObjectMapperWrapper.deserialize(NewBooking.class,
                NewHttpWrapper.prepare().init().
                post("/booking", ObjectMapperWrapper.serialize(booking)).
                header(ACCEPT, FORMAT).header(CONTENT, FORMAT).
                execute());
        assertThat(newBooking.getBookingid(),notNullValue());
    }

    @Tag(value = "booking")
    @Test
    public void partialUpdateTest() throws IOException {
        LOG.info("Get booking Id test");
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
        LOG.info("Get booking Id test");
        assertThat(NewHttpWrapper.prepare().init().delete("/booking/3").
                header(CONTENT, FORMAT).
                header("cookie", "token=" + HttpWrapper.generateToken().getToken()).
                execute(), equalTo("Created"));
    }

    @Test
    public void pingTest() throws IOException {
        LOG.info("Get booking Id test");
        assertThat(NewHttpWrapper.prepare().init().get("/ping").execute(), equalTo("Created"));
    }

}
