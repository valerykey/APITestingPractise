package com.valeryk.test;

import com.valeryk.Base;
import com.valeryk.HttpWrapper;
import com.valeryk.ObjectMapperWrapper;
import com.valeryk.dataprovider.Data;
import com.valeryk.valueobjects.response.Token;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;


public class TokenTest extends Base {

    final static String TOKEN_URL = "https://restful-booker.herokuapp.com/auth";
    final static String HEADER_FIELD = "Content-Type";
    final static String HEADER_VALUE = "application/json";
    final static String TOKEN_USERNAME = "admin";
    final static String TOKEN_WRONG_PASSWORD = "password1";

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void tokenCreationPositiveTest() throws IOException {

        Token token = HttpWrapper.generateToken();
        assertThat(token.getToken(),notNullValue());

    }

    @Test
    public void tokenCreationNegativeTest() throws IOException {

        HttpPost httpPost = HttpWrapper.post(TOKEN_URL, HEADER_FIELD);
        httpPost.setEntity(ObjectMapperWrapper.serialize(Data.createAuthentificationObject(TOKEN_USERNAME, TOKEN_WRONG_PASSWORD)));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        assertThat(httpclient.execute(httpPost, getResponse()), equalTo("{\"reason\":\"Bad credentials\"}"));

    }
}

