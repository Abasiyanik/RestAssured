package day07;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;

public class C3_Hr_ORDS_Test {

    //http://54.90.101.103:1000/ords/hr/regions/3
    @BeforeAll
    public static void setUp(){
        baseURI = "http://54.90.101.103:1000";
        basePath = "ords/hr" ;
    }

    @AfterAll
    public static void tearDown(){
        reset();
    }

}

