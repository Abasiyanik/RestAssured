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

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class C1_OpenMovieDB_Test {
    //OpenDB den sonuc alip kontrol edecegiz
    //http://www.omdbapi.com/?t=matrix&type=movie&api=5b5d0fe8

    @BeforeAll
    public static void setUp(){
        RestAssured.baseURI = "http://www.omdbapi.com" ;
    }
    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("Test Search Movie or OpenMovieDB Test")
    @Test
    public void testMovie() {

        //DB Movie den ilgili query ile alakali bilgi test edilecek

    given()
            .queryParam("apiKey","513b45c")
            .queryParam("t","the matrix").
    when()
            .get().prettyPeek().  // our request URL is already complete , do not need to add anything here//our url is already completed so we do not need to add anything
    //prettyPeek hem yazar hem de devreder
    then()
            .statusCode(is(200))
            .contentType(ContentType.JSON)
            .body("Title", is("The Matrix"))
            .body("Year", is("1999"))
            .body("imdbID", is("tt0133093"))
            .body("Ratings[0].Source", is("Internet Movie Database")); }


    @DisplayName("Getting the log of request and response")
    @Test
    public void testSendingRequestAndGetTheLog(){

        given()
                .queryParam("apiKey","513b45c" )
                .queryParam("t","John Wick")
                // logging the request should be in given section
                .log().all().
//                .log().uri().
//                  .log().params().
        when()
                .get().
                then()
                // logging the response should be in then section
//                .log().all()
//                .log().status()
//                .log().body()
                .log().ifValidationFails()
                .statusCode(  is(200)  )
                .body("Plot", containsString("ex-hit-man") )
                // second Ratings source is Rotten Tomato
                .body("Ratings[1].Source",is("Rotten Tomatoes") )

        ;


    }

}
