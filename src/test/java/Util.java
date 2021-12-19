import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class Util {
    public static RequestSpecification getRequestSpecification() {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri("https://reqres.in/api")
                .log().all();

        return requestSpecification;
    }
}
