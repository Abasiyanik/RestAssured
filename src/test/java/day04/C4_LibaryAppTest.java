package day04;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
//library app de bizler body verdik ve burada auth ve verificationlar kullandik
// burada body de postman de email ve apassword vardi
// amac bir login request gonderecegiz login account
//check tit
public class C4_LibaryAppTest {
    private static String myToken;
    @BeforeAll
    public static void setUp() {
        baseURI = "http://library1.cybertekschool.com";
        basePath = "/rest/v1";
        //myToken=someUtility.getToken()
    }

    @AfterAll
    public static void tearDown() {
        reset();
    }


    @DisplayName("Testing POST/Login Endpoint")
    @Test @Order(2)
    public void testLogin(){// libaray api, public and no need autentiacation but email
/*
Labraraien email and password should be provided
librarian69@library
KNPXrm3S
 */

        //authentication is the front of your driving license tell who you re,Authorization is the back page that show which vehicles you can drive.
        myToken=
                        given()
                                .log().all()
                             //   .auth().basic("admin", "admin")
                                .contentType(ContentType.URLENC)
                                .formParam("email", "librarian69@library")
                                .formParam("password", "KNPXrm3S").
                       when()
                                .post("/login").//post it
                       then()
                                .log().all()
                                .assertThat()
                                .statusCode(is(200))
                                .contentType(ContentType.JSON)
                                .body("token", is(not (emptyString())))
                                .extract()//to extract one date
                                .jsonPath()
                                .getString("token")
                ;//tum bu codlar sonucu hem verification yapilir hem de token bilgisi alinir elde edilir
        System.out.println("tokeninfo = " + myToken);


    }
    @DisplayName("Testing GET/Login Endpoint")
    @Test @Order(1)
    public void testGetDashboard() {// libaray api, public and no need autentiacation but email


        given()
                .log().all()
                .header("x-library-token", myToken).
                //.formParam("email", "librarian69@library")
                //.formParam("password", "KNPXrm3S").
         when()
                .get("/dashboard_stats").//post it
         then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
              //  .body("token", is(not (emptyString())))
                ;

    }
}