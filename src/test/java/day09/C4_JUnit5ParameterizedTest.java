package day09;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import lombok.Value;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class C4_JUnit5ParameterizedTest {


@ParameterizedTest
    @ValueSource(ints={5,6,7,8,9})
    public void test1(int myNumber){
//value soruce otomatik olarak myNumberi donusturur ve her defasinda farkli bir numara verir. 5, 6, 7, hepsi de bir seyin altindadir
    System.out.println("myNumber="+myNumber);

    //assert 5,6,7,8,9, all less than 10

    assertTrue(myNumber<10);

}

// we will use csv file for parametrized test

    @ParameterizedTest
    @CsvFileSource(resources="/zipcode.csv", numLinesToSkip=1)
    public void test2(String zip){

        System.out.println("zip"+zip);

        //http://api.zippopotam.us/us/90210
        //api.zippopotam.us/us/{zipcode}
        //baseurl:api.zippopotam.us
        //endpoint is /us/{zipcode}

        //lets test

       /* given()
                .baseUri("http://api.zippopotam.us")
                .pathParam("zipcode", 10000).
        when()
                .get("/us/{zipcode}").
        then()
                .statusCode(200);*/
        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("zipcode" , zip).
                when()
                .get("/us/{zipcode}").
                then()
                .statusCode(200) ;//hepsini kontrol etti ve testten sonra okey cekti

        //mesela 66666 denedik hata verdi cunku 6666 yok volla


    }
    @ParameterizedTest//without parametrized test this can not happen. parametirzed test birer birer verileri koyar
    @CsvFileSource(resources="/country_zipcode.csv", numLinesToSkip=1)
    public void testCountryZipCodeCombo(String country, String zip){
        given()
                .log().uri()
                .baseUri("https://api.zippopotam.us")
                .pathParam("country" , country)
                .pathParam("zipcode" , zip).
                when()
                .get("/{country}/{zipcode}").
                then()
                .statusCode(200) ;

    }

}
