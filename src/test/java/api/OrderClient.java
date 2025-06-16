package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Order;

import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    @Step("Создать заказ")
    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .get("/api/v1/orders");
    }
}
