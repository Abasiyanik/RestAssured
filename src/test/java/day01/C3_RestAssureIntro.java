package day01;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class C3_RestAssureIntro {

    @DisplayName("Spartan GET/ api?hello Endpoint Test")
    @Test
    public void TestHello(){
        /*
        * this is the public ip I shared for spartan2
        * use it if you do not have your own
        * if you have your own, use your own ip
        * http://54.158.53.176:8000/api/hello
        *
        * make sure this is what is imported for data type Response
        * import io.restassured.response.Response;
        * */
        Response response =get("http://54.158.53.176:8000/api/hello");
        //get status code out of this response object

        int statusCode_200= response.statusCode();
        System.out.println("status Code :"+response.statusCode());

        assertThat(statusCode_200, equalTo(200));// 200 numarali code olmali

        //now wondering what the body is; what the body is os
        // how to pretty print entire response object
        response.prettyPrint();// this print the body//ayrica printLn e gerek kalmaz
        //System.out.println(response.prettyPrint());// this print the body
        //hadi bunu yani body i yani paylo yu baska bir stringe atayayalim
        String payLoadAsStr=response.prettyPrint();
        //assertThat the body is Hellof from Sparta
        assertThat(payLoadAsStr, is("Hello from Sparta"));
// headerdaki content type i 3 sekilde elde edilir
        System.out.println(response.getHeader("Content-Type"));
        System.out.println(response.getContentType());
        System.out.println(response.contentType());
        String contenttype =response.contentType();
        //hadi bunu assert edelim
        assertThat(contenttype, is("text/plain;charset=UTF-8"));
        assertThat(contenttype, containsString("text/plain"));
        assertThat(contenttype, startsWith("text"));

        // Easy way to work with Content-type without typing much
        // We can use ContentType Enum from RestAssured to easily get main part content-type
        // ContentType.TEXT -->> text/plain as Enum
        // startWith accept a String object
        // so use toString method to turn ContentType.TEXT to String so we can use it startWith

        assertThat(contenttype, startsWith (ContentType.TEXT.toString()));//
        assertThat(contenttype, is (not(ContentType.JSON)));





    }

}
