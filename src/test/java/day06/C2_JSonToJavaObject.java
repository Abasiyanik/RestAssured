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
import pojo.SpartanRead;
import utility.ConfigurationReader;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import utility.SpartanUtil;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class C2_JSonToJavaObject {

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

    @DisplayName("Get 1Data with Save REsponse Json as Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsMap(){//DESTERILIZATION

//lets get all response
        Response response=
                given()
                        .log().all()
                        .auth().basic("admin","admin")
                        .accept(ContentType.JSON)
                        .pathParam("id", 64).
                when()
                        .get("/spartans/{id}")
                        .prettyPeek();
//how ot get a json path object

        JsonPath jp=response.jsonPath();

// how ot convert all json to jave object
        // JsonPath jp = response.jsonPath();
        Map<String,Object> responseMap =  jp.getMap("") ;
        System.out.println("responseMap = " + responseMap);

        //goal is to convert it to something

        SpartanRead sp=jp.getObject("",SpartanRead.class);
        System.out.println("sp = " + sp);


        // jp.getMap("")
//        {
//            "id": 65,
//                "name": "Bart",
//                "gender": "Male",
//                "phone": 1607274537
//        }
/**
 * {
 *     "id": 5,
 *     "name": "Sayeem",
 *     "gender": "Male",
 *     "phone": 1231231230
 * }
 * jsonPath to get whole json object is just empty string
 *
 * assume this is a car object
 * {
 *     "make":"Honda"
 *     "color" : "white"
 *     "engine" : {
 *                   "type" : "v8"
 *                   "horsepower" : 350
 *                }
 * }
 * jsonPath for horse power -->> engine.horsepower
 * jsonPath for engine itself -->> engine
 * jsonPath for entire car JsonObject -->> ""
 *
 *
 */

    }
    @DisplayName("Get All Data and Save Response JsonArray As Java Object")
    @Test
    public void getOneSpartanAndSaveResponseJsonAsJavaObject(){


        Response response = given()
                .auth().basic("admin", "admin").
                        when()
                .get("/spartans") ;

        JsonPath jp = response.jsonPath() ;

        List<SpartanRead> allSpartanPOJOs = jp.getList("", SpartanRead.class);


        //System.out.println("allSpartanPOJOs = \n" + allSpartanPOJOs);
        allSpartanPOJOs.forEach(System.out::println);

    }

    // Send request to /api/spartans/search endpoint
    // save your jsonArray from search result into
    // List of SpartanRead POJO



}
