package tests;

import io.restassured.RestAssured;
import models.UserBodyModel;
import models.UserResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresWithModelsTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successfulLoginTest() {

        UserBodyModel authData = new UserBodyModel();
        authData.setEmail("aleo83@rambler.ru");
        authData.setName("Alex");

        UserResponseModel response = given()
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .when()
                .post("/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(UserResponseModel.class);
        assertEquals("Missing password", response.getError());

    }

    @Test
    void successfullCreateUserTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");
        authData.setJob("student");

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
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");
        authData.setJob("student");
        authData.setId(111);


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
                .body("id", is(111));
    }

    @Test
    void successfullUpdateUserNameWithPutTest() {

        UserBodyModel authData = new UserBodyModel();
        authData.setJob("student");

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
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");

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








