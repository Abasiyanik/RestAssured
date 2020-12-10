package day02;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;





public class C1_SpartanTest {

    //first spartan task in postman
    // get all spartans

    @DisplayName("Spartan GET/ api?hello Endpoint Test")
    @Test
    public void testGetAllStartan() {



        Response response =get("http://54.158.53.176:8000/api/spartans");

        response.prettyPrint();



    }

}

