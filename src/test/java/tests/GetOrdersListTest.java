package tests;

import api.OrderClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetOrdersListTest {
    @Test
    @Step("Получить список заказов")
    public void getOrdersList() {
        OrderClient orderClient = new OrderClient();
        Response response = orderClient.getOrdersList();
        response.then().assertThat()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));

        System.out.println(response.body().asString());

    }
}
