package test;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.io.IOException;

public class TokenTest extends BaseTest{

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void tokenCreationPositiveTest() throws IOException {

        HttpPost httpPost = new HttpPost("https://restful-booker.herokuapp.com/auth");
        httpPost.setHeader("Content-Type", "application/json");
        String json = "{\"username\":\"admin\", \"password\":\"password123\"}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);

        ResponseHandler<String> responseHandler = getResponse();
        CloseableHttpResponse response = httpclient.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));

        String responseBody = httpclient.execute(httpPost, responseHandler);
        assertThat(responseBody, containsString("token"));
    }

    @Test
    public void tokenCreationNegativeTest() throws IOException {

        HttpPost httpPost = new HttpPost("https://restful-booker.herokuapp.com/auth");
        httpPost.setHeader("Content-Type", "application/json");
        String json = "{\"username\":\"admin\", \"password\":\"password1\"}";
        StringEntity stringEntity = new StringEntity(json);
        httpPost.setEntity(stringEntity);
        ResponseHandler<String> responseHandler = getResponse();
        CloseableHttpResponse response = httpclient.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        String responseBody = httpclient.execute(httpPost, responseHandler);
        assertThat(responseBody, equalTo("{\"reason\":\"Bad credentials\"}"));

    }

}

