package day08;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;
import static io.restassured.RestAssured.*;

public class C2_ORDS_API_DB_Test extends HR_ORDS_TestBase {

    @DisplayName("Testing the connection with query")
    @Test
    public void testDB_Connection(){

        DB_Utility.runQuery("SELECT * FROM REGIONS");

        DB_Utility.displayAllData();
        }

    /**
     * send an get/regions/{region_id} request with region_id of 3
     * check status code is 200
     * save it as Region POJO after status check
     * get your epected result from database query
     * SELECT * FROM REGIONS
     * SAVE THE THIRD ROW AS A MAP
     *
     * VERIFY THE DATA FROM RESPONSE MATCH DATA FROM DATABASE
     */

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data")
    @Test
    public void testRegionDataFromResponseMatchDB_Data(){
        int myID=4;
        Response response=given()
                .pathParam("region_id", myID).
        when()
                .get("/regions/{region_id}").//prettyPeek();
        then()
                .log().body()
                .statusCode(200)
                .extract()
                .response();

        //save it as region poje

        Region r3 =response.as(Region.class);//r3 = Region{region_id=3, region_name='Asia'}
        System.out.println("r3 = " + r3);

        //get our query
//DB_utility ye bak ve sonra bak
        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID= "+myID);
        Map<String,String> expectedResultMap=DB_Utility.getRowMap(1);
        System.out.println("expectedResultMap = " + expectedResultMap);

        //validation starts now

        assertThat(r3.getRegion_id()+"",equalTo(expectedResultMap.get("REGION_ID")));// REGION ID NUMARA IDI STRING YAPTIK
        assertThat(r3.getRegion_name(),is(expectedResultMap.get("REGION_NAME")));

    }
    //know map to map comparision

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data With Both Maps")
    @Test
    public void testRegionDataFromResponseMatchDB_Data2() {
        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();
        /**
    //save the response json as a Map object
       Map<String,String> actualResultMap=jp.getMap("", String.class, String.class);//normalde json string string map olarak kaydeder
       //actualResultMap = {region_id=3, region_name=Asia, links=[{rel=self, href=http://54.90.101.103:1000/ords/hr/regions/3}, {rel=edit, href=http://54.90.101.103:1000/ords/hr/regions/3}, {rel=describedby, href=http://54.90.101.103:1000/ords/hr/metadata-catalog/regions/item}, {rel=collection, href=http://54.90.101.103:1000/ords/hr/regions/}]}
        //bunlarin hepsini istemoruz linkleri remove etmem lazim NASIL

        System.out.println("actualResultMap = " + actualResultMap);
      //  actualResultMap.remove("links");
        System.out.println("actualResultMap = " + actualResultMap);//actualResultMap = {region_id=3, region_name=Asia} //vola

        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID= "+myID);
        Map<String,String> expectedResultMap=DB_Utility.getRowMap(1);
      //  assertThat(actualResultMap, equalTo(expectedResultMap));//bu olmadi cunku basliklar olmadi
        assertThat(actualResultMap.get("region_id"), equalTo(expectedResultMap.get("REGION_ID")));
**/
        // save the response json as a Map object
        // Here we are calling the overloaded version of getMap method with 3 params
        // 1. jsonPath String
        // 2. Data type Map key
        // 3. Data type Map value
        // so we can make sure we get exactly what we asked for
        Map<String, String> actualResultMap = jp.getMap("",String.class,String.class);
        // do not need to remove extra links from json result
        // because we are checking key value pair , anything we dont check will not matter
        System.out.println("actualResultMap = " + actualResultMap);

        DB_Utility.runQuery("SELECT * FROM REGIONS WHERE REGION_ID = "+myID ) ;
        Map<String,String> expectedResultMap =  DB_Utility.getRowMap(1) ;

        System.out.println("expectedResultMap = " + expectedResultMap);

        // since the keyname is different in both map we can not directly
        // compare map to map object
        // we have to compare the value of key step by step
        assertThat(  actualResultMap.get("region_id") ,
                equalTo( expectedResultMap.get("REGION_ID") ) );
        assertThat(actualResultMap.get("region_name") ,
                equalTo( expectedResultMap.get("REGION_NAME") ) );

    }

    @DisplayName("Testing GET /regions/{region_id} Data Match Database Data With Just value by value")
    @Test
    public void testRegionDataFromResponseMatchDB_Data3() {

        int myID = 3;
        JsonPath jp = given()
                .pathParam("region_id", myID).
                        when()
                .get("/regions/{region_id}").
                        then()
                .log().body()
                .statusCode(200)
                .extract()
                .jsonPath();

        String actualRegionId   = jp.getString("region_id") ;
        String actualRegionName = jp.getString("region_name") ;

        DB_Utility.runQuery("SELECT REGION_ID, REGION_NAME FROM REGIONS WHERE REGION_ID = "+ myID) ;
        String expectedRegionId   = DB_Utility.getColumnDataAtRow(1,"REGION_ID") ;
        String expectedRegionName = DB_Utility.getColumnDataAtRow(1,"REGION_NAME") ;

        assertThat( actualRegionId , is(expectedRegionId ) );
        assertThat( actualRegionName , equalTo(expectedRegionName ) );


    }


}
