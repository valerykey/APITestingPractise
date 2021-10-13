import io.restassured.response.Response;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import com.valeryk.Const;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith({BaseTest.class})
public class GenderApiPositiveTest extends BaseTest{

    @Tag("positive")
    @Test
    public void defineGenderTest() {
        Response response = given()
                .when()
                .queryParam(Const.ParameterName, Const.CorrectValueName)
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getString("gender"),notNullValue());
        assertThat(response.jsonPath().getString("count"),greaterThanOrEqualTo("100"));
    }

    @Tag("positive")
    @Test
    public void batchUsageTest(){
        Map<String,List<String>> map = Map.of(Const.ParameterName,
                List.of("lera","charlie","elena", "sasha", "harry", "fred", "george", "anna", "vika", "petr", "ron"));

        Response response = given()
                .when()
                .queryParams(map)
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getString("gender"),notNullValue());
    }

    @Tag("positive")
    @Test
    public void localizationTest(){
        Response response = given()
                .when()
                .queryParam(Const.ParameterName,Const.CorrectValueName)
                .queryParam(Const.ParameterCountryId, Const.ValueCountryId)
                .get()
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getString("gender"),notNullValue());
        assertThat(response.jsonPath().getString("count"),greaterThanOrEqualTo("100"));
    }
}
