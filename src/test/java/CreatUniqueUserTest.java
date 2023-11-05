import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;public class CreatUniqueUserTest {
    private static final String BURGER_URI = "https://stellarburgers.nomoreparties.site/";
    private static final User USER = new User("elenaIva@yandex.ru", "4534", "Elena");
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
        //System.out.println("!!!!!!!!!" + accessToken);
    }

    @After
    public void deleteUser () {
        if (client.loginUser(accessToken).extract().statusCode() == 200)
    }
    //    if (client.login(Credentials.fromCourier(COURIER))
    //            .extract().statusCode() == 200 ) {
    //        Integer id = client.login(Credentials.fromCourier(COURIER))
     //               .extract().body().jsonPath().getInt("id");
     //       client.deleteCourierByID(new Id(id));
    //    }
   // }

}
