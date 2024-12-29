package com.elija.infra.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class PizzaResourceIT {
    @Test
    void testPizzaEndpoint() {
        given()
                .when().get("/pizza")
                .then()
                .statusCode(200);
    }

}