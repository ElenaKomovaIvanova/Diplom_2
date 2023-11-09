import groovy.transform.ToString;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.awt.*;

import static io.restassured.RestAssured.given;
public class UserServiceClient {
    private static final String CREATE_USER = "/api/auth/register";
    private static final String AUTH_USER = "/api/auth/login";
    private static final String SINGOUT_USER = "/api/auth/logout";

    private static final String DELETE_USER = "/api/auth/user";
    private static final String GET_USER = "/api/auth/user";
    private static final String CHANG_USER = "/api/auth/user";

    private RequestSpecification requestSpecification;

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public ValidatableResponse createUser(User user) {
        return given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(user)
                .post(CREATE_USER)
                .then()
                .log()
                .all();
    }

    public ValidatableResponse loginUser(User user) {
        return given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(user)
                .post(AUTH_USER)
                .then();
    }


    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .delete(DELETE_USER)
                .then()
                .log()
                .all();

    }

    public ValidatableResponse getUser (String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .get(GET_USER)
                .then()
                .log()
                .all();

    }

    public ValidatableResponse changUserEmail (User user, String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .body(user)
                .patch(CHANG_USER)
                .then()
                .log()
                .all();
    }


}
