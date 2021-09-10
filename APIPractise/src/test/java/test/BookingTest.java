package test;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class BookingTest extends BaseTest{

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void geteIDsTest() throws IOException {
        HttpGet httpGet = new HttpGet("https://restful-booker.herokuapp.com/booking");

        ResponseHandler<String> responseHandler = getResponse();

        String responseBody = httpclient.execute(httpGet, responseHandler);
        System.out.println(responseBody);
    }

    @Test
    public void getBookingTest() throws IOException {
        HttpGet httpGet = new HttpGet("https://restful-booker.herokuapp.com/booking/1");
        httpGet.setHeader("Accept", "application/json");
        ResponseHandler<String> responseHandler = getResponse();

        String responseBody = httpclient.execute(httpGet, responseHandler);
        System.out.println(responseBody);
    }

}
