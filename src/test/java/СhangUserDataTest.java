import org.junit.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class СhangUserDataTest {
    private static final String BURGER_URI = "https://stellarburgers.nomoreparties.site/";
    private static final User USER = new User("177h@jjj.jj", "45c34", "Elena");
    private UserServiceClient client = new UserServiceClient();
    String accessToken;
    String accessTokeNoAuth;
    private static final String EMAIL = "17zxc@zxc.zxc";
    private static final String PASSWORD = "112233";
    private static final String NAME = "zxczxc";
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
    public void changUserEmailTest() {

        User user1 = new User(EMAIL, PASSWORD, NAME);
        ValidatableResponse response1 = client.changUserEmail(user1,accessToken);
        response1.assertThat()
                .statusCode(200);
    }

    @Test
    public void changUserNameTest() {

        User user1 = new User(EMAIL, PASSWORD, NAME);
        ValidatableResponse response1 = client.changUserEmail(user1,accessToken);
        response1.assertThat()
                .statusCode(200);
    }

    @Test
    public void changUserEmailNoAuthTest() {

        User user1 = new User(EMAIL, PASSWORD, NAME);
        accessTokeNoAuth = "";
        ValidatableResponse response1 = client.changUserEmail(user1,accessTokeNoAuth);
        response1.assertThat()
                .statusCode(401)
                .body("success", CoreMatchers.is(false));
    }



    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }



}
