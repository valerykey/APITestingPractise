import com.valeryk.Const;
import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenderApiNegativeTest extends BaseTest {

    @Tag("negative")
    @Test
    public void missValueNegativeTest() {
        Response response = given()
                .when()
                .queryParam(Const.ParameterName,"")
                .get()
                .then()
                .statusCode(422)
                .extract().response();
       assertThat(response.jsonPath().getString("error"),equalTo("Missing 'name' parameter"));
    }

    @Tag("negative")
    @Test
    public void invalidValueNegativeTest() {
        Response response = given()
                .when()
                .queryParam(Const.ParameterName, Const.WrongValueName)
                .get()
                .then()
                .statusCode(422)
                .extract().response();
        assertThat(response.jsonPath().getString("error"),equalTo("Missing 'name' parameter"));
    }

    @Tag("negative")
    @Test
    public void localizationNegativeTest(){
        Response response = given()
                .when()
                .queryParam(Const.ParameterName,Const.CorrectValueName)
                .queryParam(Const.ParameterCountryId, "R")
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getString("gender"),nullValue());
    }

}
