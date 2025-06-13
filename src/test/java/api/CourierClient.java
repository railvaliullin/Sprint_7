package clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "http://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Залогиниться курьером")
    public Response login(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public Response delete(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}
