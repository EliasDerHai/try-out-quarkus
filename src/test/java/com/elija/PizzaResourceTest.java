package com.elija;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class PizzaResourceTest {
    @Test
    void testPizzaEndpoint() {
        given()
                .when().get("/pizza/examples")
                .then()
                .statusCode(200);
    }

}