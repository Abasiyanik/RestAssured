package day05;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import utility.ConfigurationReader;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class C1_ExtractData_test {
/*
extract() method of ResAssued
enable you to extract data after validation
in then section of the method chaining
 */

    @BeforeAll
    public static void setUp() {
        //baseURI = "http://54.161.137.82:8000";
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api";
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }



    @DisplayName("Testing GET /api/spartans/search with Basic auth")
    @Test
    public void testSearchAndExtractData(){
// search for nameContains : a , gender Female
        // verify status code is 200
        // extract jsonPath object after validation
        // use that jsonPath object to get the list of all results
        // and get the numberOfElements field value
        // compare those 2
    JsonPath jp=
            given()
                    .auth().basic("admin", "admin")
                    .queryParam("nameContains", "Ol")
                    .queryParam("gender", "Female").
             when()
                    .get("/spartans/search").
             then()
                    .assertThat()
                    .statusCode(is(200))
                    .extract()
                    .jsonPath()

            ;


// get the list of all names in String
        List<String> allNames =  jp.getList("content.name");
        System.out.println("allNames = " + allNames);

        // we are getting numberOfElements field from json result
        // since it's a top level key , json path will be just numberOfElements
        int numOfElements = jp.getInt("numberOfElements");
        System.out.println("numOfElements = " + numOfElements);


        // verifying the numOfElements match the size of list we got
        assertThat(numOfElements , equalTo( allNames.size() )    );

}


}
