package day08;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Region;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;
import static io.restassured.RestAssured.*;

public class ORDS_API_DB_Test extends HR_ORDS_TestBase {

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
        int myID=3;
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


    }




}
