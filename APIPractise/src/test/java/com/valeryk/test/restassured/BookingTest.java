package com.valeryk.test.restassured;

import com.valeryk.Base;
import com.valeryk.HttpWrapper;
import com.valeryk.dataprovider.Data;
import com.valeryk.valueobjects.response.Booking;
import com.valeryk.valueobjects.response.BookingDates;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingTest {

    static private Cookie cookie;
    final static String CONTENT = "Content-Type";
    final static String FORMAT = "application/json";
    final static String ACCEPT = "Accept";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Tag(value = "rest")
    @Test
    public void getBookingIDTest() {
                 given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .extract().response();
    }

    @Tag(value = "rest")
    @Test
    public void getBookingTest() {
        Response response = given()
                .header(ACCEPT, FORMAT)
                .when()
                .get("/booking/1")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getString("firstname"),notNullValue());
        assertThat(response.jsonPath().getString("bookingdates"),notNullValue());
    }

    @Tag(value = "rest")
    @Test
    public void createBooking() throws IOException {

        Booking booking = Data.createBookingObject("Jim", "Brown", 111, true, new BookingDates("2018-01-01", "2019-01-01"), "breakfast");

        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .body(booking)
                .when()
                .post("/booking")
                .then()
                .extract().response();
        assertThat(response.jsonPath().getString("bookingid"),notNullValue());
        assertThat(response.jsonPath().getString("booking"),notNullValue());
    }

    @Tag(value = "rest")
    @Test
    public void updateBookingTest() throws IOException{
       Booking booking = Data.createBookingObject("Jim", "Brown", 111, true, new BookingDates("2018-01-01", "2019-01-01"), "breakfast");
       Response response = given()
                .header(ACCEPT, FORMAT)
                .and()
                .header(CONTENT, FORMAT)
                .and()
                .header("cookie", "token=" + HttpWrapper.generateToken().getToken())
                .body(booking)
                .when()
                .put("/booking/1")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getString("firstname"),equalTo("Jim"));
        assertThat(response.jsonPath().getString("bookingdates"),notNullValue());
    }



}
