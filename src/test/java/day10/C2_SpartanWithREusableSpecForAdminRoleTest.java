package day10;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.*;
import pojo.Spartan;
import utility.ConfigurationReader;
import static io.restassured.RestAssured.* ;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.* ;

import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utility.ConfigurationReader;
import utility.SpartanUtil;

import java.util.concurrent.TimeUnit;

public class C2_SpartanWithREusableSpecForAdminRoleTest {
    //response ve request parameterlarinin over and over ayazilmasina gerek yok
    //birkere yazariz ve her yerde kullaniirz A guzel bir ornek

    static RequestSpecification givenSpec ;
    static ResponseSpecification thenSpec ;
    static RequestSpecification postReqSpec ;
    static Spartan randomSpartanPayload ;


    @BeforeAll
    public static void setUp(){

        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        basePath = "/api" ;

        givenSpec =  given().log().all()
                .auth().basic("admin","admin") ;
// log().all() will not work with expect()
        // in order to make it work we need to use different method
        // logDetail(LogDetail.ALL) to provide how much we want to log
        thenSpec = expect().logDetail(LogDetail.ALL)
                .statusCode(is(200) )
                .contentType(ContentType.JSON) ;
        randomSpartanPayload = SpartanUtil.getRandomSpartanPOJO_Payload();
        postReqSpec = given().spec(givenSpec)
                .contentType(ContentType.JSON)
                .body(randomSpartanPayload) ;
    }
    @AfterAll
    public static void cleanUp(){
        RestAssured.reset();
    }

    @DisplayName("GET /api/spartans/{id} Endpoint Test")
    @Test
    public void testOneSpartan(){

        given()
                .spec(givenSpec)
                .pathParam("id",34).
                when()
                .get("/spartans/{id}").
                then()
                .spec(thenSpec)
        ;
        // alternative way , since the data type of givenSpec is already a RequestSpecification
        givenSpec
                .pathParam("id",34).
                when()
                .get("/spartans/{id}").
                then()
                .spec(thenSpec)
        ;

    }

    @DisplayName("POST /api/spartans Endpoint Test")
    @Test
    public void testPost1Data() {

        Spartan randomSpartanPayload = SpartanUtil.getRandomSpartanPOJO_Payload();

        RequestSpecification postReqSpec =  given().spec(givenSpec)
                .contentType(ContentType.JSON)
                .body(randomSpartanPayload) ;

        ResponseSpecification postResponseSpec =  expect().logDetail(LogDetail.ALL)
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!") )
                .body("data.id",notNullValue() )
                .body("data.name", is( randomSpartanPayload.getName() ) )
                .body("data.gender", is(randomSpartanPayload.getGender() ))
                .body("data.phone", is(randomSpartanPayload.getPhone()) )
                ;
        given()
                .spec(postReqSpec).
                when()
                .post("/spartans").
                then()
                .spec(postResponseSpec)
        ;
    }

    @DisplayName("Test POST /api/spartans Endpoint negative scenario ")
    @Test
    public void testBadRequest400responseBody(){
        Spartan badPayload = new Spartan("A","A",100L) ;
        String nameErrorMessage     = "name should be at least 2 character and max 15 character" ;
        String genderErrorMessage   = "Gender should be either Male or Female" ;
        String phoneErrorMessage    = "Phone number should be at least 10 digit and UNIQUE!!" ;

        //negative test yapiyoruz. error mesajini alip gostecegiz nasil yapariz
        //oncer request gonder
       /* RequestSpecification postReqSpec =  given().spec(givenSpec)
                .contentType(ContentType.JSON)
                .body(badPayload) ;

        ResponseSpecification postResponseSpec =  expect().logDetail(LogDetail.ALL)
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("success",is("A Spartan is Born!") )
                .body("data.id",notNullValue() )
                .body("data.name", is( badPayload.getName() ) )
                .body("data.gender", is(badPayload.getGender() ))
                .body("data.phone", is(badPayload.getPhone()) )
                ;
        given()
                .spec(postReqSpec).
                when()
                .post("/spartans").
                then()
                .spec(postResponseSpec)*///bunlar yerine hepsini basa topladik voila
        given()
        .spec(postReqSpec)//basta olurturuldu
        .body(badPayload).when().post("/spartans").
                then()
                .log().all()
                .statusCode(400) //problem olusmali
        // error 400 olustu ve negative test gecti
        //3 hata array olmali ve errors olarak
        .body("errors", hasSize(3))
                //verify the rerors
//        .body("errors[0].defaultMessage", is("Gender should be either Male or Female"))
       // .body("errors[2].defaultMessage", is("name should be at least 2 character and max 15 character"))
//      .body("errors[2].defaultMessage", is("Phone number should be at least 10 digit ad UNIQUE!!"))
            //    .body("message", containsString("Error count:3")) bu faiil oldu zira ordur boyle oldu
                .body("errors.defaultMessage",
                        containsInAnyOrder(nameErrorMessage,genderErrorMessage,phoneErrorMessage))
                .body("message", containsString("Error count: 3"))
        ;

/*
 verify the errors field has value of json array with 3 items
 verify default messages for those errors :
    "Gender should be either Male or Female"
    "name should be at least 2 character and max 15 character"
    "Phone number should be at least 10 digit and UNIQUE!!"
verify the message field contains "Error count: 3"
 */
    }

    @DisplayName("GET /api/spartans Endpoint Test")
    @Test
    public void testAllSpartan(){

        // log().all() will not work with expect()
        // in order to make it work we need to use different method
        // logDetail(LogDetail.ALL) to provide how much we want to log
        given()
                .spec(givenSpec).
                when()
                .get("/spartans").
                then()
                .spec(thenSpec)
        ;


    }
    @DisplayName("GET /api/spartans check response time < 1 second")
    @Test
    public void testResponseTime(){
        given()
                .spec(givenSpec).//return specification
                when()
                .get("/spartans").
                then()
                .spec(thenSpec)//response sepecification
                .time( lessThan(2000L) )// unit yok ama milisec diye algilar
                .time( lessThan(2L)  , TimeUnit.SECONDS ) ; //unit ekliyoru
    }
}
