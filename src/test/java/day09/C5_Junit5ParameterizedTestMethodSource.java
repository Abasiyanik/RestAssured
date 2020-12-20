package day09;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import testbase.HR_ORDS_TestBase;
import utility.DB_Utility;

import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class C5_Junit5ParameterizedTestMethodSource extends HR_ORDS_TestBase {

    @ParameterizedTest(name = "Iteration {index} : countryID is {0}")
    @MethodSource("getAllCountryIDs")
    public void testDB(String countryID){

        System.out.println("countryID = " + countryID);
        given().log().uri().
                when().get("/countries/{country_id}",countryID).
                then().log().status();

    }
    public static Stream<String> getAllCountryIDs(){
        DB_Utility.runQuery("SELECT * FROM COUNTRIES");
        return DB_Utility.getColumnDataAsList(1).stream();
    }

    @ParameterizedTest(name = "Row [{index}] : {0}")
    @MethodSource("getAllCountryDataAsListOfMapStream")
    public void testAllCountryData(Map<String,String> expectedRowMap){

        System.out.println("expectedRowMap = " + expectedRowMap);
        given()
                .log().uri()
                .pathParam("country_id",expectedRowMap.get("COUNTRY_ID") ).
                when()
                .get("/countries/{country_id}").
                then()
                .log().status()
                .body("country_id",          is(expectedRowMap.get("COUNTRY_ID") ) )
                .body("country_name" ,       is(expectedRowMap.get("COUNTRY_NAME") ) )
                .body("region_id.toString()",is(expectedRowMap.get("REGION_ID") ) )

        ;

    }
    public static Stream<Map<String,String>> getAllCountryDataAsListOfMapStream(){
        DB_Utility.runQuery("SELECT * FROM COUNTRIES");
        return DB_Utility.getAllDataAsListOfMap().stream();
    }
}
