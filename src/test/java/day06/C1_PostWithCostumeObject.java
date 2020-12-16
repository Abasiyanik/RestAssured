package day06;
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
import pojo.Spartan;
import utility.ConfigurationReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import utility.SpartanUtil;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class C1_PostWithCostumeObject {
    @BeforeAll
    public static void setUp(){
        //RestAssured.filters().add(new AllureRestAssured() ) ;
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }


    @DisplayName("Add 1 Data with POJO as body")
    @Test
    public void testAddDataWithPojo(){
   // Spartan sp1= new Spartan("B20 user", "Male", 123487567676L);
    //   System.out.println(sp1);
     //random uretti

        Spartan sp1 = SpartanUtil.getRandomSpartanPOJO_Payload();

        System.out.println(sp1);
        given()
                .log().all()
                //.auth().basic(ConfigurationReader.getProperty("spartan.admin.username"), ConfigurationReader.getProperty("spartan.admin.password"))
                .auth().basic("admin","admin")
                .contentType(ContentType.JSON)
                .body(sp1).
                when()
                .post("/spartans").
                then()
                .log().all()
                .assertThat()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success", is("A Spartan is Born!"))
                .body("data.name",is(sp1.getName()))
                .body("data.gender",is(sp1.getGender()))
                .body("data.phone",is(sp1.getPhone()));
        ;


    }}