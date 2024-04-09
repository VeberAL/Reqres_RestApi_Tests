package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class UserSpec {
  public static RequestSpecification userRequestSpec = with()
                .filter(withCustomTemplates())
                .log().uri()
                .log().body()
                .log().headers()
                .contentType(JSON);

   public static ResponseSpecification userClientStatusResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(STATUS)
                .log(BODY)
                .build();

   public static ResponseSpecification userOkStatusResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(STATUS)
                .log(BODY)
                .build();

   public static ResponseSpecification userCreatedStatusResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .log(STATUS)
                .log(BODY)
                .build();

   public static ResponseSpecification userNoContentStatusResponseSpec = new ResponseSpecBuilder()
                .expectStatusCode(204)
                .log(STATUS)
                .log(BODY)
                .build();
}
