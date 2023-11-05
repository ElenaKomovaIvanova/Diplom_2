import groovy.transform.ToString;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
public class UserServiceClient {
    private static final String CREATE_USER = "/api/auth/register";
    private static final String AUTH_USER = "/api/auth/login";
    private static final String SINGOUT_USER = "/api/auth/logout";

    private static final String DELETE_COURIER = "/api/v1/courier/";

    private RequestSpecification requestSpecification;

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public ValidatableResponse createUser(User user) {
        return  given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .body(user)
                .post(CREATE_USER)
                .then()
                .log()
                .all();
    }

   public ValidatableResponse loginUser(String accessToken) {
                 return  given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .auth().oauth2("accessToken").and()
                .when()
                .post(AUTH_USER)
                .then();
    }


}
