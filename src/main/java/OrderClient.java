import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String GET_INGR = "/api/ingredients";
    private static final String CREATE_ORDER = "/api/orders";
    private static final String RECEIV_ORDER = "/api/orders";

    private RequestSpecification requestSpecification;

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    @Step("Получение ингридиентов")
    public ValidatableResponse getInsr() {
        return given()
                .spec(requestSpecification)
                .header("Content-type", "application/json")
                .get(GET_INGR)
                .then()
                .log()
                .all();
    }

    @Step("Создание заказа авторизованным пользователем")
    public ValidatableResponse createOrderAuth(Ingredients ingredients, String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .body(ingredients)
                .post(CREATE_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Получение заказа")
    public ValidatableResponse ReceivOrders(String accessToken) {
        return given()
                .spec(requestSpecification)
                .header("Authorization", accessToken)
                .get(RECEIV_ORDER)
                .then()
                .log()
                .all();
    }

    @Step("Установка корректного списка ингредиентов для создания заказа")
    public String[] setIngredient() {
        String[] ingr = new String[2];
        ingr[0] = "61c0c5a71d1f82001bdaaa70";
        ingr[1] = "61c0c5a71d1f82001bdaaa71";
        return ingr;
    }

    @Step("Установка некорректного списка ингредиентов для создания заказа")
    public String[] setIncorrectIngredient() {
        String[] ingr = new String[2];
        ingr[0] = "61c0c5ahhh";
        ingr[1] = "61c0c5alll";
        return ingr;
    }
}
