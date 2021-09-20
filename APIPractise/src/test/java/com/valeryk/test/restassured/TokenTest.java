package com.valeryk.test.restassured;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TokenTest {
    final static String TOKEN_URL = "https://restful-booker.herokuapp.com/auth";

    @Test
    public void tokenCreationPositiveTest() {
     /*   RestAssured.baseURI = TOKEN_URL;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.request(Method.POST);
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);
        assertThat(response.getStatusCode(), equalTo(200));*/

    }
}
