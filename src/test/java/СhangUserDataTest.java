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
    private static final User USER = new User("elenaW@ya.ru", "4534", "Elena");
    private UserServiceClient client = new UserServiceClient();
    String accessToken;
    String email;
    String name;

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
    public void changUserEmail_expOk_test() {

        User user1 = new User("vlad@ya.ru", "4534", "Elena");
        ValidatableResponse response1 = client.changUserEmail(user1,accessToken);
        response1.assertThat().body("email", CoreMatchers.is(email));
    }

    @Test
    public void changUserName_expOk_test() {

        User user1 = new User("elenaW@ya.ru", "4534", "Vlad");
        ValidatableResponse response1 = client.changUserEmail(user1,accessToken);
        response1.assertThat().body("name", CoreMatchers.is(name));
    }

    @Test
    public void changUserEmail_expFalse_test() {

        User user1 = new User("vlad@ya.ru", "4534", "Elena");
        accessToken = "";
        ValidatableResponse response1 = client.changUserEmail(user1,accessToken);
        response1.assertThat().body("success", CoreMatchers.is(false));
    }



    @After
    public void deleteUser() {
        if (accessToken != null) {
            client.deleteUser(accessToken);
        }
    }



}
