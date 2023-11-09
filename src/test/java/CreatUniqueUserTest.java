import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;public class CreatUniqueUserTest {
    private static final String BURGER_URI = "https://stellarburgers.nomoreparties.site/";
    private static final User USER = new User("mam@uh.ru", "4534", "Elena");
    private static final User USER1 = new User("", "4534", "Elena");
    private UserServiceClient client = new UserServiceClient();
    String accessToken;

    @Before
    public void client() {
        RequestSpecification requestSpecification =
                new RequestSpecBuilder().setBaseUri(BURGER_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        client.setRequestSpecification(requestSpecification);
    }

    @Test
    public void createUniqueUser_expOk_test() {

        ValidatableResponse response = client.createUser(USER);
        response.assertThat().body("success", CoreMatchers.is(true));
        accessToken = response.extract().body().jsonPath().getString("accessToken");
    }

    @Test
    public void createNoUniqueUser_expFalse_test() {

        ValidatableResponse response = client.createUser(USER);
        ValidatableResponse response1 = client.createUser(USER);
        response1.assertThat().body("success", CoreMatchers.is(false));
        accessToken = response.extract().body().jsonPath().getString("accessToken");
    }

    @Test
    public void createNoEmailUser_expFalse_test() {

        ValidatableResponse response = client.createUser(USER1);
        response.assertThat().body("success", CoreMatchers.is(false));
        accessToken = response.extract().body().jsonPath().getString("accessToken");
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }
}
