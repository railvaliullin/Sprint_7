package tests;

import api.CourierClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojo.Courier;
import utils.RandomLogin;

import static org.hamcrest.core.IsEqual.equalTo;

public class CreateCourierTest {
    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        courier = new Courier(
                RandomLogin.getRandomLogin(10),
                RandomLogin.getRandomLogin(10),
                RandomLogin.getRandomLogin(10));
    }

    @Test
    @Step("Успешное создание курьера")
    public void createCourierSuccess() {
        Response response = courierClient.create(courier);
        response.then().assertThat()
                .statusCode(201)
                .body("ok", equalTo(true));

        Response loginResponse = courierClient.login(courier);
        courierId = loginResponse.then().extract().path("id");

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Проверка на создание пользователя с логином, который уже есть")
    public void createDuplicateCourier() {
        courierClient.create(courier);
        Response loginResponse = courierClient.login(courier);
        courierId = loginResponse.then().extract().path("id");

        Response response = courierClient.create(courier);
        response.then().assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Проверка на обязательные поля, отсутствие логина")
    public void createCourierNotLogin() {
        Courier courierWithoutLogin = new Courier(null, "password", "name");
        Response response = courierClient.create(courierWithoutLogin);
        response.then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Проверка на обязательные поля, отсутствие пароля")
    public void createCourierNotPassword() {
        Courier courierWithoutPassword = new Courier("login", null, "name");
        Response response = courierClient.create(courierWithoutPassword);
        response.then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Проверка на обязательные поля, отсутствие имени")
    public void createCourierNotName() {
        Courier courierWithoutPassword = new Courier("login", "password", null);
        Response response = courierClient.create(courierWithoutPassword);
        response.then().assertThat()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        System.out.println(response.body().asString());

    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }

}