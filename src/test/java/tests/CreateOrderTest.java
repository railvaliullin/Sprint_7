package tests;

import api.OrderClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pojo.Order;
import utils.RandomLogin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest {
    private OrderClient orderClient;
    private Order order;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
        order = new Order();
        // Общие параметры для всех тестов
        order.setFirstName(RandomLogin.getRandomLogin(10));
        order.setLastName(RandomLogin.getRandomLogin(10));
        order.setAddress(RandomLogin.getRandomLogin(20));
        order.setMetroStation("1");
        order.setPhone("+7 917 917 09 17");
        order.setRentTime(3);
        order.setDeliveryDate("2025-12-06");
        order.setComment("Test");
    }

    static Stream<List<String>> colorProvider() {
        return Stream.of(
                Arrays.asList("BLACK"),
                Arrays.asList("GREY"),
                Arrays.asList("BLACK", "GREY"),
                null
        );
    }

    @ParameterizedTest
    @MethodSource("colorProvider")
    @Step("Создание заказа с разными цветами: {arguments}")
    public void createOrderDifferentColor(List<String> colors) {
        order.setColor(colors);

        Response response = orderClient.create(order);
        response.then().assertThat()
                .statusCode(201)
                .body("track", notNullValue());

        System.out.println(response.body().asString());
    }
}