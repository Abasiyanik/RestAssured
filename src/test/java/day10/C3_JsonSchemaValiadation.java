package day10;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testbase.SpartanAdminTestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class C3_JsonSchemaValiadation extends SpartanAdminTestBase {

    @DisplayName("Testing the structure of GET / api/sprtans{id} response")
    @Test
    public void testGetSingleSpartanScheme(){
        //burada amac json correct structurea sahip olup olmamais, yoksa hangi veri olup olmamasi degil yani

// veriler scema ya uyuyor mu
////bubun icin bir dependency ekledik
//        <dependency>
//      <groupId>io.rest-assured</groupId>
//      <artifactId>json-schema-validator</artifactId>
//      <version>4.3.3</version>
//      <scope>test</scope>
//</dependency>

        given()
                .spec(adminReqSpec)
                .pathParam("id",34).
                when()
                .get("/spartans/{id}").
                then()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("singleSpartanSchema.json") ) ;
//burada yukleyecegimiz veri schemaya uygunmu degil mi o balilyor. online bir islem degil zira veri elimizde schemda elimizde csv olarak






    }



}
