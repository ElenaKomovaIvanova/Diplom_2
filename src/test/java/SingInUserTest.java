import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SingInUserTest {
    private static final String BURGER_URI = "https://stellarburgers.nomoreparties.site/";
    private static final User USER = new User("mam@uh.ru", "4534", "Elena");
    private static final User USER1 = new User("mamsd@uh.ru", "4534", "Elena");
    private UserServiceClient client = new UserServiceClient();
    String accessToken;

    @Before
    public void client() {
        RequestSpecification requestSpecification =
                new RequestSpecBuilder().setBaseUri(BURGER_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpecification(requestSpecification);
        ValidatableResponse response = client.createUser(USER);
        accessToken = response.extract().body().jsonPath().getString("accessToken");
    }

    @Test
    public void LogInTest() {

        ValidatableResponse response1 = client.loginUser(USER);
        response1.assertThat()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));

    }

    @Test
    public void LogIn_expfalse_test() {
        ValidatableResponse response1 = client.loginUser(USER1);
        System.out.println(response1.extract().statusCode());
        response1.assertThat()
                .statusCode(401)
                .body("success", CoreMatchers.is(false));

    }

   @After
   public void deleteUser () {
        client.deleteUser(accessToken);
   }
}
