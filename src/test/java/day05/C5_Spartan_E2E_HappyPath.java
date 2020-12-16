package day05;
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

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utility.ConfigurationReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import utility.SpartanUtil;

import java.util.Map;

import static io.restassured.RestAssured.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class C5_Spartan_E2E_HappyPath {

    private static Map<String, Object> payloadMap;
    private static int newID;


    @BeforeAll
    public static void setUp() {
        // baseURI = "http://54.161.137.82:8000";
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";
        payloadMap = SpartanUtil.getRandomSpartanReqestPayload();
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }


//create , update and delete

    @DisplayName("1. Testing POST/ api/spartans Endpoint")
    @Test
    @Order(1)
    public void testAddData() {
        newID =
                given()
                        .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                        .contentType(ContentType.JSON)
                        .body(payloadMap)
                        .log().all().
                        when()
                        .post("/spartans").
                        then()
                        .log().all()
                        .assertThat()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .body("data.name", is(payloadMap.get("name")))
                        .body("data.gender", is(payloadMap.get("gender")))
                        .body("data.phone", equalTo(payloadMap.get("phone")))
                        .extract()//to extract the id
                        .jsonPath()
                        .getInt("data.id")
        ;
        System.out.println("newID = " + newID);
    }

    @DisplayName("2. Testing GET/ api/spartans Endpoint")
    @Test
    @Order(2)
   // @AfterEach
    public void testgetData() {

        given()
                .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                .contentType(ContentType.JSON)
                .body(payloadMap)
                .pathParam("id", newID)
                .log().all().
        when()
                .get("/spartans/{id}").
            //    .get("/spartans/" + newID).
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
               // .contentType(ContentType.JSON)
                .body("name", is(payloadMap.get("name")))
                .body("gender", is(payloadMap.get("gender")))
               .body("id", is(newID))
                .body("phone", equalTo(payloadMap.get("phone")));
        System.out.println("after aech======================================================");
    }

    @DisplayName("3. Testing upload/ api/spartans Endpoint")
    @Test
    @Order(3)
    public void testUpdate1SpartanData() {
//we want ot ave different payload so we can update
        //option is rerun the utuiliy method to override
        // existiong map object with newly generated faker map object
        payloadMap=SpartanUtil.getRandomSpartanReqestPayload();

        given()
                .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                .contentType(ContentType.JSON)
                .body(payloadMap)
                .pathParam("id", newID)
                .log().all().
        when()
                .put("/spartans/{id}").
                //    .get("/spartans/" + newID).
                        then()
                .log().all()
                .assertThat()
                .statusCode(204)
                .body(emptyString());
//to make usre the update actually happened
        given()
                .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                .contentType(ContentType.JSON)
                .body(payloadMap)
                .pathParam("id", newID)
                .log().all().
        when()
                .get("/spartans/{id}").
                //    .get("/spartans/" + newID).
        then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                // .contentType(ContentType.JSON)
                .body("name", is(payloadMap.get("name")))
                .body("gender", is(payloadMap.get("gender")))
                .body("id", is(newID))
                .body("phone", equalTo(payloadMap.get("phone")));
    }

    @DisplayName("4. Testing DELETE /api/spartans/{id} Endpoint")
    @Test @Order(4)
    public void testDelete1SpartanData(){

        given()
                .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                .pathParam("id" , newID)
                .log().all().
        when()
                .delete("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is(204) )
                .body( emptyString() ) ;

        // in order to make sure the delete actually happened
        // i want to make another get request to this ID expect 404
        given()
                .auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                .pathParam("id" , newID)
                .log().all().
        when()
                .get("/spartans/{id}").
        then()
                .log().all()
                .assertThat()
                .statusCode( is (404) ) ;
  }

}