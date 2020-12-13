package day04;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class C3_SpartanUpdatingTest {
    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.161.137.82:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Testing PUT /api/spartans{id} with string body")
    @Test
    public void testUpdatingSingleSpartan() {
        String updateSpartanStr = "  {\n" +
                "        \"name\": \"Ahmed\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }";
        System.out.println(updateSpartanStr);

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 454)
                .body(updateSpartanStr).
        when()
                .put("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))//no body so body bakilmaz
                .header("Date", notNullValue())
                .body(emptyString())
             //   .contentType(ContentType.JSON)
        // .body("success", is("A Spartan is Born!"))
               // .body("data.name", is("Ahmed"))
               // .body("data.gender", is("Male"))
              //  .body("data.phone", is(9876543210L))
        ;

    }
    @DisplayName("Testing PATCH /api/spartans/{id} with String body")
    @Test
    public void testPartialUpdatingSingleSpartanWithStringBody(){

        // update the name to B20 Patched
        // {"name" : "B20 Patched"}
        String patchBody = "{\"name\" : \"B20 Patched\"}";

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 454)
                .body(patchBody).
        when()
                .patch("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode(is(204))//body for 204 is always empety and we can validate it using emptyString() matcher
                .header("Date", notNullValue())
                .body(emptyString())// means no body NO BODY NOBODY NOBODI NO BUD NOB NO OO

        ;
    }

    @DisplayName("Testing DELETE /api/spartans/{id}")
    @Test
    public void testDeleteSingleSpartan(){


        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .pathParam("id", 451).
      //          .body(patchBody).
                when()
                .delete("/spartans/{id}").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(204))//body for 204 is always empety and we can validate it using emptyString() matcher
                .header("Date", notNullValue())
                .body(emptyString())// means no body NO BODY NOBODY NOBODI NO BUD NOB NO OO
        ;
    }




}
