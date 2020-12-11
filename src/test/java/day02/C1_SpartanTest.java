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





public class C1_SpartanTest {

    @BeforeAll
    public static void setUp(){
        // baseURI and basePath is static fields of RestAssured Class
        // Since we static imported RestAssured, we can access all static field directly just like it's in our own class here
        // you can use static way as below
        //RestAssured.baseURI = "http://100.26.101.158:8000";
        // or you can directly use as below
        baseURI="http://54.158.53.176:8000";
        //RestAssured.basePath = "/api" ;
        basePath="/api";
        //RestAssured.baseURI="http://54.158.53.176:8000";
        //RestAssured.basePath="/api";
        // baseURI + basePath + whatever you provided in http method like get post
        // for example :
        // get("/spartans") -->>  get(baseURI + basePath + "/spartans")
   }

   @AfterAll
   public static void tearDown(){
        //restetting the value fo baseURI, basePAth
       reset();
       //RestAssured.reset();
   }

    //first spartan task in postman
    // get all spartans

    @DisplayName("Spartan GET/ api?")
    @Test
    public void testGetAllStartan() {
// send a get request to above endpoint
        // save the response
        // print out the result
        // try to assert the status code
        // content type header

       //Response response = get("http://54.158.53.176:8000/api/spartans");
        Response response = get("/spartans");//beforeall da cagirdigimiz icin sadece /spartans yeterli

        response.prettyPrint();
        int statusCode_200 = response.statusCode();
        System.out.println("status Code :" + response.statusCode());

        assertThat(statusCode_200, equalTo(200));
        assertThat(response.contentType(), is(ContentType.JSON.toString()));

    }

    @DisplayName("testing / api/ spartans endpoint xml response")
    @Test
    public void testGetAllSpartanXML() {
        /**
         * given
         *      --- RequestSpecification
         *      used to provide additional information about the request
         *      base url  base path
         *      header , query params , path variable , payload
         *      authentication authorization
         *      logging , cookie
         * when
         *      --- This is where you actually send the request with http method
         *      -- like GET POST PUT DELETE .. with the URL
         *      -- We get Response Object after sending the request
         * then
         *      -- ValidatableResponse
         *      -- validate status code , header , payload , cookie
         *      -- responseTime
         */

        //accept type header

        given()
                .header("accept" , "application/xml").//bu accept header postmande de usttedir

        when()
                //.get("http://54.158.53.176:8000/api/spartans").
                .get("/spartans").
        then()
                .statusCode(200)
                .header("Content-Type","application/xml" ); //bu da response headerdir ve gelen response header dir

//content ype header
        // This will do same exact thing as above in slightly different way
        // since accept header and content type header is so common , RestAssured has good support or those header by providing method directly rather than using header method we used above

        given()
                .accept(ContentType.XML).

        when()
                        //.get("http://54.158.53.176:8000/api/spartans").
                        .get("/spartans").
        then()
                .statusCode(is(200))
                .and()
                .contentType(ContentType.XML);
//tell me the difference between "accept header" and "content type header"
/*
You are hungry for a burger.
you send a request to your waiter saying "I'm sending "Content-type" burger and burger only"
the waiter gives you a respond with ACCEPT burger.
accept header ---> for response, saying you can accept this data as XML or Json  (to receive request)
Content-Type --->  you are telling postman what kind of data you are sending.  (to send request)
 */

    }
}
