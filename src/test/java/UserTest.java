import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTest {
    String id;


    @Test
    public void verifyGetUsersApi() {
        Response response = Util.getRequestSpecification().queryParam("page", 2).get("/users");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void verifyCreateUserApi() {
        JsonObject user = new JsonObject();
        user.addProperty("name", "ravi");
        user.addProperty("job", "Engineer");

        Response response = Util.getRequestSpecification()
                .header("Content-Type", "Application/Json")
                .body(user.toString())
                .post("/users");
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 201);
        id = response.jsonPath().get("id");
    }

    @Test(dependsOnMethods = "verifyCreateUserApi")
    public void verifyGetUserApi() {
        Response response = Util.getRequestSpecification().queryParam("id", id).get("/users");
        response.then().log().all();
        //Assert.assertEquals(response.getBody().jsonPath().get("name"),"ravi");
    }

    @Test(dependsOnMethods = "verifyCreateUserApi")
    public void verifyUpdatedUser() {
        JsonObject user = new JsonObject();
        user.addProperty("name", "Ravi");
        user.addProperty("job", "Developer");

        Response response = Util.getRequestSpecification()
                .header("Content-Type", "Application/Json")
                .body(user.toString())
                .pathParam("id", id)
                .put("/users/{id}");
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
        //================================
        Response getRes = Util.getRequestSpecification().pathParams("id", id).get("/users/{id}");
        getRes.then().log().all();
        //Assert.assertEquals(getRes.getBody().jsonPath().get("name"),"Ravi");
    }



}
