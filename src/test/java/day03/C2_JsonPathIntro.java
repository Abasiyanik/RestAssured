package day03;
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
import utility.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class C2_JsonPathIntro {

    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("spartan.base_url");
        baseURI="http://54.158.53.176:8000";
        basePath="/api";
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

    @DisplayName("extractiong data out of Spartan Json Object")
    @Test
    public void test1SpartanPayLoad() {
        /*
        send a request to ger 1 spartan
        by providing path params with valid id
        save it into Response object
        NEW: create an object with type JsonPAth using by calling the method JsonPath() on response object
        extract id, name, gender, phone
        and save it into variable of correct type
        */
        Response response= given()
                                    .pathParam("id", 34).
                            when()
                                    .get("/spartans/{id}")
                                    .prettyPeek()//it is printing and giving everything//it return all response
                ;

        //response.prettyPrint();
        //JsonPath is used to navigate through the json payload and
        //extract the value according to the valid "jsonpath" provided

        JsonPath jp=response.jsonPath();

        int myId=jp.getInt("id");
        String myName=jp.getString("name");
        String myGender=jp.getString("gender");
        long myPhone =jp.getLong("phone");

        System.out.println("myId = " + myId);
        System.out.println("myName = " + myName);
        System.out.println("myPhone = " + myPhone);
        System.out.println("myGender = " + myGender);

        }
    @DisplayName("extractiong datafrom Json Array Response")
    @Test
    public void getAllSpartanExtractData() {
  //tum veriyi alip istedigimiz kismi ayiririz
        //Response response=get("/spartans");
        //JsonPath jp=response.jsonPath();

        JsonPath jp = get("/spartans").jsonPath();

        //get the first json object name field

        System.out.println("jp.getString(\"name[0]\")="
                +jp.getString("name[0]"));

//get the first json object phone field

        System.out.println("jp.getLong(\"phone[0]\")="
                +jp.getLong("phone[0]"));

//get the seventh json object gender field

        System.out.println("jp.getString(\"gender[6]\")="
                +jp.getString("gender[6]"));

//getting all the name fileds from the jsonArray Response
        //a nd staring as a List
        List<String> allNames=jp.getList("name");//jp tum isimleri alir bir liste yapar hurra
        System.out.println("allNames = " + allNames);

        //getting all the phone fileds fro the jsonArray response
        // and storing as a list
        List<Long>allPhones=jp.getList("phone");
        System.out.println("allPhones = " + allPhones);

    }
    //send request ot this rewuest urL
    //http://54.158.53.176:8000/api/spartans/search?nameContains=de&gender=Male
    //get the name of the first guy in the results
    //get all names, all phones save it as list
    //save the value of field called empty under payable in the response
    //print it out

    @DisplayName("testsing /spartans.search and extracting data")
    @Test
    public void testSearch() {

           JsonPath jp=     given()
                                    .queryParam("nameContains", "de")
                                    .queryParam("gender", "Male").
                            when()
                                    .get("/spartans/search") //gerisini queryParam halleder
                                    .jsonPath();//bu sayede ne kadar sonuc varsa jp ye yuklenir

        //simdi jp den istenen bilgi sagilmalidir oda 1) birincinin adi
        System.out.println("jp.getString(\"content[0].name\") = " + jp.getString("content[0].name"));//Golden
// ucuncunun adamin phonei
        System.out.println("jp.getLong(\"content[2].phone\") = " + jp.getLong("content[2].phone"));
//how to get all the names
        List<String> allNames=jp.getList("content.name");
        System.out.println("allNames = " + allNames);
        System.out.println("jp.getList(\"content.name\") = " + jp.getList("content.name"));
//how to get all phone numbers
        List<Long>allPhones=jp.getList("content.phone");
        System.out.println("allPhones = " + allPhones);
        System.out.println("jp.getList(\"content.phone\") = " + jp.getList("content.phone"));

        System.out.println("value of field empty :" +
                jp.getBoolean("pageable.sort.empty"));

    }



}
