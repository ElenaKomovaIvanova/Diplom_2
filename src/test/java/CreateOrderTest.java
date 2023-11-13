import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateOrderTest {
    private static final String BURGER_URI = "https://stellarburgers.nomoreparties.site/";
    private static final User USER = new User("maw@uh.ru", "4534", "Elena");
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
    public void getInsr() {
        clientOrder.getInsr();
    }

    @Test
    public void createOrderAuthUserTest() {
        ingr = clientOrder.setIngredient();
        Ingredients ingredients = new Ingredients(ingr);
        ValidatableResponse response = clientUser.createUser(USER);
        accessToken = response.extract().body().jsonPath().getString("accessToken");
        ValidatableResponse response1 = clientOrder.createOrderAuth(ingredients, accessToken);
        response1.assertThat()
                .statusCode(200)
                .body("success", CoreMatchers.is(true));
    }

    @Test
    public void createOrderNoAuthUserTest() {
        ingr = clientOrder.setIngredient();
        Ingredients ingredients = new Ingredients(ingr);
        accessToken = "";
        ValidatableResponse response = clientOrder.createOrderAuth(ingredients, accessToken);
        response.assertThat()
                .statusCode(401)
                .body("success", CoreMatchers.is(false));
    }

    @Test
    public void createOrderAuthNoIngrTest() {
        Ingredients ingredients = new Ingredients(ingr);
        ValidatableResponse response = clientUser.createUser(USER);
        accessToken = response.extract().body().jsonPath().getString("accessToken");
        ValidatableResponse response1 = clientOrder.createOrderAuth(ingredients, accessToken);
        response1.assertThat()
                .statusCode(400)
                .body("success", CoreMatchers.is(false));
    }

    @Test
    public void createOrderAuthIncorrectIngTest() {
        ingr = clientOrder.setIncorrectIngredient();
        Ingredients ingredients = new Ingredients(ingr);
        ValidatableResponse response = clientUser.createUser(USER);
        accessToken = response.extract().body().jsonPath().getString("accessToken");
        ValidatableResponse response1 = clientOrder.createOrderAuth(ingredients, accessToken);
        response1.assertThat()
                .statusCode(500);

    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            clientUser.deleteUser(accessToken);
        }
    }

}
