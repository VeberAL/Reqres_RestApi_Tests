package tests;

import io.restassured.RestAssured;
import models.UserBodyModel;
import models.CreateAndUpdateUserResponseModel;
import models.LoginUserResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReqresWithModelsTests {

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

        LoginUserResponseModel response = step("Make request", ()-> 
              given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(LoginUserResponseModel.class));
        step("Check response", ()-> 
        assertEquals("Missing password", response.getError()));
    }

    @Test
    void successfullCreateUserTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");
        authData.setJob("student");

        CreateAndUpdateUserResponseModel response = step("Make request", ()->
              given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateAndUpdateUserResponseModel.class));
        step("Check response", ()-> {
        assertEquals("Alex", response.getName());
        assertEquals("student", response.getJob());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
        )};
    }

    @Test
    void successfullCreateUserWithIdTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");
        authData.setJob("student");
        authData.setId(111);


        CreateAndUpdateUserResponseModel response = step("Make request", ()->
              given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(CreateAndUpdateUserResponseModel.class));
        step("Check response", ()-> {
        assertEquals("Alex", response.getName());
        assertEquals("student", response.getJob());
        assertEquals(111, response.getId());
        assertNotNull(response.getCreatedAt());
        )};
    }

    @Test
    void successfullUpdateUserNameWithPutTest() {

        UserBodyModel authData = new UserBodyModel();
        authData.setJob("student");
      
        CreateAndUpdateUserResponseModel response = step("Make request", ()->
            given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .put("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(CreateAndUpdateUserResponseModel.class)));
        step("Check response", ()-> 
        assertEquals("student", response.getJob()));
    }

    @Test
    void successfullUpdateUserJobWithPatchTest() {
        UserBodyModel authData = new UserBodyModel();
        authData.setName("Alex");

        CreateAndUpdateUserResponseModel response = step("Make request", ()->
             given()
                .filter(withCustomTemplates())
                .body(authData)
                .contentType(JSON)
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(CreateAndUpdateUserResponseModel.class)));
        step("Check response", ()-> 
        assertEquals("Alex", response.getName()));
    }

    @Test
    void deleteUserTest() {
        step("Make request", ()->
             given()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .when()
                .delete("/users/2")
                .then()
                .log().status()
                .statusCode(204));
    }
}
