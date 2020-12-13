package day05;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class C2_HamcrestCollectionSupport {


@Test
public void testList() {

    List<Integer> numList= Arrays.asList(4,5,6,7,9,88,99);
    //use hamcrest matcher totest the size of this list
    assertThat(numList, hasSize(7));

    //assert thatt his list conatins 9
    assertThat(numList, hasItem(9));// item 9 var mi
//assert that this list
    assertThat(numList, hasItems(7,88));//item 7 ve 88 varmi

    //assert that everyItems in the list are more than 3
    //assertThat(numList, everyItem(greaterThan(3)));//  hepsi 3 den buyuk biri kucuk olsa olmaz
    assertThat(numList, everyItem(is (greaterThan(3))));//  hepsi 3 den buyuk biri kucuk olsa olmaz


    List<String> allNames  = Arrays.asList("Rory Hogan", "Mariana","Olivia","Gulbadan","Ayxamgul","Kareem","Virginia","Tahir Zohra") ;
    assertThat(allNames, hasSize(8));
    assertThat(allNames, hasItems("Virginia", "Rory Hogan", "Ayxamgul"));//

//check every itmes has letter
    assertThat(allNames, everyItem( containsString ("a")));//
    assertThat(allNames, everyItem( containsStringIgnoringCase ("a")));//

//how to do and or in hamcrest syntaz
    //allOf=> and andyof+. or

    //AND , and logic, all of the matchers should match or it failes
    assertThat("Murat Degirmenci", allOf(startsWith("Mu"), containsString("men")));

        //OR LOGIC ==> as long as one matcher match it will pass
    assertThat("Ramazan Alic", anyOf(is("Ramazan"), endsWith("ic")));


    //=====================hamcrest fail olunce detayli bilgi verir===========
}



}
