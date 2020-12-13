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

public class C2_SpartanAddingUpdatingLoging {
    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.161.137.82:8000";
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }

    @DisplayName("Testing GET /api/spartans with Basic auth")
    @Test
    public void testAllSpartanWithBasicAuth() {

        given()
                .log().all()
                .auth().basic("admin", "admin").
                when()
                .get("/spartans").
                then()
                .log().all()
                .statusCode(is(200));
    }

    @DisplayName("Add 1 Data with Raw Json String POST /api/spartans")
    @Test
    public void testAddOneData() {
/*
 {
        "id": 373,
        "name": "Jermaine",
        "gender": "Male",
        "phone": 1231231234
    }
 */
        String newSpartanStr = "  {\n" +
                "        \"name\": \"Muhammed\",\n" +
                "        \"gender\": \"Male\",\n" +
                "        \"phone\": 9876543210\n" +
                "    }";
        System.out.println(newSpartanStr);

        given()
                .log().all()
                .auth().basic("admin", "admin")
                .contentType(ContentType.JSON)
                .body(newSpartanStr).
                when()
                .post("/spartans").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Muhammed"))
                .body("data.gender", is("Male"))
                .body("data.phone", is(9876543210L))
        ;
    }

    @DisplayName("Add 1 Data with Raw Json String POST /api/spartans")
    @Test
    public void testAddOneDataWithMapAsBody() {
        Map<String, Object> payloadMap = new LinkedHashMap<>();
        payloadMap.put("name", "Tucky");
        payloadMap.put("gender", "Male");
        payloadMap.put("phone", 9876543210L);

        System.out.println("payloadMap = " + payloadMap);

        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(payloadMap).
                when()
                .post("/spartans").
                then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Tucky"))
                .body("data.gender", is("Male"))
                .body("data.phone", is(9876543210L))
        ;


    }

    @DisplayName("Add 1 Data with External json fıle POST. apı.spartan")
    @Test
    public void testAddOneDataWithJsonFileAsBody() {
        // Create a file called singleSpartan.json right under root directory
        // with below content
    /*
    {
        "name": "Olivia",
        "gender": "Female",
        "phone": 6549873210
    }
    add below code to point File object to this singleSpartan.json
     */
        File externalJson = new File("singleSpartan.json");
        given()
                .auth().basic("admin", "admin")
                .log().all()
                .contentType(ContentType.JSON)
                .body(externalJson).
        when()
                .post("/spartans").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.name", is("Abasiyanik"))
                .body("data.gender", is("Female"))
                .body("data.phone", is(3456543210L))
        ;

    }
}