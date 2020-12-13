package day05;

import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.containsString;

public class C3_AssertingCollectionIntheChain {


    @BeforeAll
    public static void setUp() {
        baseURI = "http://54.161.137.82:8000";
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
       //aim: to verify all the items should be feamle and contains a
       //do it in the chain
        //ve sayi belli olacak


        given()
                .log().all()
                .auth().basic("admin", "admin")
                .queryParam("nameContains", "a")
                .queryParam("gender", "Female").
        when()
                .get("/spartans/search").
       then()
                .log().all()
                .assertThat()
                .statusCode(is(200))
                //once kactane yaini sayi 41 olabilir mi
                .body("numberOfElements", equalTo(41))
                .body("content", hasSize(41))
               .body("content.name", everyItem(containsStringIgnoringCase("a")))
              //  .body("content.gender", everyItem(equalTo("Female")))
               .body("content.gender", everyItem(is ("Female")))
                        // we want to check each/every item which has "a"

        //.assertThat(containsString ("a")))
                ;
    }

}
