package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successfulLoginTest() {
        String authData = "{\"email\": \"peter@klaven\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void successfullCreateUserTest() {
        String authData = "{\"name\": \"Alex\", \"job\": \"student\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Alex"))
                .body("job", is("student"));
    }

    @Test
    void successfullCreateUserWithIdTest() {
        String authData = "{\"name\": \"Alex\", \"job\": \"student\", \"id\": \"111\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("Alex"))
                .body("job", is("student"))
                .body("id", is("111"));
    }

    @Test
    void successfullUpdateUserNameWithPutTest() {
        String authData = "{\"job\": \"student\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .put("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("student"));
    }

    @Test
    void successfullUpdateUserJobWithPatchTest() {
        String authData = "{\"name\": \"Alex\"}";

        given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .patch("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("Alex"));
    }

    @Test
    void deleteUserTest() {

        given()
                .log().uri()
                .when()
                .delete("/api/users/2")
                .then()
                .log().status()
                .statusCode(204);
    }
}