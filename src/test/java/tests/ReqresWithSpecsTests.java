package tests;

import io.restassured.RestAssured;
import models.CreateUserResponseModel;
import models.LoginUserResponseModel;
import models.UpdateUserResponseModel;
import models.UserBodyModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.UserSpec.*;

public class ReqresWithSpecsTests {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void successfulLoginTest() {

        UserBodyModel authData = new UserBodyModel();
        authData.setEmail("aleo83@rambler.ru");
        authData.setName("Alex");

        LoginUserResponseModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(userClientStatusResponseSpec)
                        .extract().as(LoginUserResponseModel.class));
        step("Check response", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void successfullCreateUserTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");
        authData.setJob("student");

        CreateUserResponseModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(userCreatedStatusResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
        step("Check response", () -> {
            assertEquals("Alex", response.getName());
            assertEquals("student", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    void successfullCreateUserWithIdTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");
        authData.setJob("student");
        authData.setId(111);


        CreateUserResponseModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(authData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(userCreatedStatusResponseSpec)
                        .extract().as(CreateUserResponseModel.class));
        step("Check response", () -> {
            assertEquals("Alex", response.getName());
            assertEquals("student", response.getJob());
            assertEquals(111, response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    void successfullUpdateUserNameWithPutTest() {

        UserBodyModel authData = new UserBodyModel();
        authData.setJob("student");

        UpdateUserResponseModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(authData)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(userOkStatusResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));
        step("Check response", () ->
                assertEquals("student", response.getJob()));
    }

    @Test
    void successfullUpdateUserJobWithPatchTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");

        UpdateUserResponseModel response = step("Make request", () ->
                given(userRequestSpec)
                        .body(authData)
                        .when()
                        .patch("/users/2")
                        .then()
                        .spec(userOkStatusResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));
        step("Check response", () ->
                assertEquals("Alex", response.getName()));
    }

    @Test
    void deleteUserTest() {
        step("Make request", () ->
                given(userRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(userNoContentStatusResponseSpec));
    }
}
