package day09;

import pojo.Country;
import pojo.Department;
import testbase.HR_ORDS_TestBase;
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
public class C1_ORD_API_PracticeTest extends HR_ORDS_TestBase {

    @DisplayName("GET /countries/{country_id} Compare Result with DB")
    @Test
    public void testResponseMatchDatabaseData(){
        //we are looking for argentina in database and test it
        //first give the argentina
        String mycountryID="AR";// this is our passparameter
//API part
        Country arPOJO=
        given()
                .pathParam("country_id",mycountryID).
        when()
                .get("/countries/{country_id}").prettyPeek()
                .as(Country.class); //if we do not need json path as is the best
        //here is the shorter way of the above one
        Country arPOJ01= get("/countries/{country_id}", mycountryID). as(Country.class);// digeri ile bu ikisi ayni sonucu verir
        //save as country pojo

        System.out.println("arPOJO = " + arPOJO);


        //now we will write r=the database part of the study
        String query="SELECT * FROM COUNTRIES WHERE COUNTRY_ID='"+mycountryID+"' ";
        System.out.println("query = " + query);
        //get the data by the query

        DB_Utility.runQuery(query);
        //bunu bir veriye esitleyelim
        Map<String, String> dbResultMap=DB_Utility.getRowMap(1);
        //it its time for assertion becoz we have pojo from api and databse

        assertThat(arPOJO.getCountry_id(), is(dbResultMap.get("COUNTRY_ID")));
        assertThat(arPOJO.getCountry_name(), is(dbResultMap.get("COUNTRY_NAME")));
        //SAVE REGION_ID FROM THE MAP AS NUMBER
        int expedctedRegionId=Integer.parseInt(dbResultMap.get("REGION_ID"));//stringi inted donusturup karsilastirdik yoksa olmayacakti


        assertThat(arPOJO.getRegion_id(), equalTo(expedctedRegionId));

    }
    @DisplayName("GET /countries Capture All CountryID and Compare Result with DB")
    @Test
    public void testResponseAllCountryIDsMatchDatabaseData(){
//we need to get all country id s and ompare with them
        //API olarak once tum countryid leri alacagiz AR, TR US vb
        //when we use as when not as : as does not take a path; if you need the path do not use as

        List<String> allCountriesIds=get("/countries").jsonPath().getList("items.country_id");// path icindeki items altindaki country_idleri
        //toplar bize verir. volla
        allCountriesIds.forEach(System.out::println);//hadi yazidralim tontonlari
        //+++++++++++++++++++++++simdi de database isine yonelelim ve query yazak

       // DB_Utility.runQuery("SELECT COUNTRY_ID FROM COUNTRIES");
        DB_Utility.runQuery("SELECT * FROM COUNTRIES");//aslinda birincisini gorebiliriz ama ikincisi daha informative
        List<String> expectedListFromDB= DB_Utility.getColumnDataAsList("COUNTRY_ID");// gelen tablodan sadece ilgili colun alinip yerlestirilir
        expectedListFromDB.forEach(System.out::println);
        //assert both list has same information
        assertThat(allCountriesIds, equalTo(expectedListFromDB));
    }


}
