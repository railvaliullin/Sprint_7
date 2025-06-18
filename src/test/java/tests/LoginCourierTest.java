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
import static org.hamcrest.core.IsNull.notNullValue;

public class LoginCourierTest {
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

        courierClient.create(courier);
        Response loginResponse = courierClient.login(courier);
        courierId = loginResponse.then().extract().path("id");
    }

    @Test
    @Step("Успешная авторизация")
    public void loginCourierSuccess() {
        Response response = courierClient.login(courier);
        response.then().assertThat()
                .statusCode(200)
                .body("id", notNullValue());

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Авторизация с неверным паролем")
    public void loginCourierWrongPassword() {
        Courier courierWithWrongPassword = new Courier(courier.getLogin(), "wrong_password");
        Response response = courierClient.login(courierWithWrongPassword);
        response.then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Авторизация с неверным логином")
    public void loginCourierWrongLogin() {
        Courier courierWithWrongLogin = new Courier("wrong_login", courier.getPassword());
        Response response = courierClient.login(courierWithWrongLogin);
        response.then().assertThat()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Авторизация без обязательных полей, отсутствует логин")
    public void loginCourierNotLogin() {
        Courier courierWithoutLogin = new Courier(null, courier.getPassword());
        Response response = courierClient.login(courierWithoutLogin);
        response.then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        System.out.println(response.body().asString());

    }

    @Test
    @Step("Авторизация без обязательных полей, отсутствует пароль")
    public void loginCourierNotPassword() {
        Courier courierWithoutPassword = new Courier(courier.getLogin(), null);
        Response response = courierClient.login(courierWithoutPassword);
        response.then().assertThat()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));

        System.out.println(response.body().asString());

    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }
}
