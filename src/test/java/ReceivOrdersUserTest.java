import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReceivOrdersUserTest {
    private static final String BURGER_URI = "https://stellarburgers.nomoreparties.site/";
    private static final User USER = new User("mam@uh.ru", "4534", "Elena");
    private OrderClient clientOrder = new OrderClient();
    private UserServiceClient clientUser = new UserServiceClient();
    String accessToken;
    String[] ingr;

    @Before
    public void client() {
        RequestSpecification requestSpecification =
                new RequestSpecBuilder().setBaseUri(BURGER_URI)
                        .setContentType(ContentType.JSON)
                        .build();
        clientUser.setRequestSpecification(requestSpecification);
        clientOrder.setRequestSpecification(requestSpecification);

    }

    @Test
    public void createReceivOrderTest() {
        ingr = clientOrder.setIngredient();
        Ingredients ingredients = new Ingredients(ingr);
        ValidatableResponse response = clientUser.createUser(USER);
        accessToken = response.extract().body().jsonPath().getString("accessToken");
        clientOrder.createOrderAuth(ingredients, accessToken);
        ValidatableResponse response1 = clientOrder.ReceivOrders(accessToken);
        response1.assertThat()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));
    }

    @Test
    public void createReceivOrder_expFalse_test() {
        ingr = clientOrder.setIngredient();
        Ingredients ingredients = new Ingredients(ingr);
        ValidatableResponse response = clientUser.createUser(USER);
        accessToken = response.extract().body().jsonPath().getString("accessToken");
        clientOrder.createOrderAuth(ingredients, accessToken);
        ValidatableResponse response1 = clientOrder.ReceivOrders("");
        response1.assertThat()
                .statusCode(401)
                .body("success", CoreMatchers.is(false));
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            clientUser.deleteUser(accessToken);
        }
    }
}
