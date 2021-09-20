package com.valeryk.test.restassured;

import com.valeryk.Base;
import com.valeryk.HttpWrapper;
import com.valeryk.ObjectMapperWrapper;
import com.valeryk.dataprovider.Data;
import com.valeryk.valueobjects.response.Booking;
import com.valeryk.valueobjects.response.BookingDates;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.codehaus.groovy.transform.SourceURIASTTransformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class BookingTest {

    static private Cookies cookies;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        cookies = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("/auth")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .getDetailedCookies();
    }

    @Test
    public void getBookingIDTest() {
        Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/booking")
                .then()
                .extract().response();
        assertThat(response.getStatusCode(), equalTo(200));
    }

    @Test
    public void getBookingTest() {
        Response response = given()
                .header("Accept", "application/json")
                .when()
                .get("/booking/1")
                .then()
                .extract().response();
        assertThat(response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("firstname"),notNullValue());
        assertThat(response.jsonPath().getString("bookingdates"),notNullValue());
    }

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

    @Test
    public void updateBookingTest() throws IOException{
       Booking booking = Data.createBookingObject("Jim", "Brown", 111, true, new BookingDates("2018-01-01", "2019-01-01"), "breakfast");

        Response response = given()
                .cookies(cookies)
                .header("Accept", "application/json")
                .and()
                .header("Content-Type", "application/json")
                .and()
                .body(booking)
                .when()
                .put("/booking/1")
                .then()
                .extract().response();
        assertThat(response.getStatusCode(), equalTo(200));
       // assertThat(response.jsonPath().getString("firstname"),notNullValue());
      //  assertThat(response.jsonPath().getString("bookingdates"),notNullValue());
    }



}
