package day02;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class C2_SingleSpartanTest {

    @BeforeAll
    public static void setUp(){
        baseURI="http://54.158.53.176:8000";
        basePath="/api";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }


    @DisplayName("testing GET/spartans/{id} endpoint")
    @Test
    public void test1Spartan() {
//I want ot get json result out
        //when i send Get request to /spartans/{id} endpoint
        //and expecting 200 ststus code
        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/67").
        then()
                .statusCode(is(200));

        //Response response = get("/spartans/67");
        //response.prettyPrint();

        //===i want to make obvious that id number (67) is path vraiable |params to uniquely identify the resurce

        given()
                .accept(ContentType.JSON)
                .pathParam("id", 68).
        when()
                .get("/spartans/{id}").
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);
//this is the easiste one . same results


        given()
                .accept(ContentType.JSON).when()
                .get("/spartans/{id}", 67).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON);



    }
    @DisplayName("payload ")
    @Test
    public void test1SpartanPayload() {
        /*
        "id": 67,
  "name": "Janette",
  "gender": "Female",
  "phone": 9887020445
}
         */
        given()
                .accept(ContentType.JSON).
        when()
                .get("/spartans/{id}", 67).
        then()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
                .body("id", is(67))
                .body("name", equalTo("Janette"))
                .body("gender", is(equalTo("Female")))
                .body("phone", equalTo(9887020445L));


}}



