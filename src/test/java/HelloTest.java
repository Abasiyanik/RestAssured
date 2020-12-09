import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;//bunu ekleyerek assertions.assertEquals daki birinci sinif gider yuha



public class HelloTest {
    /*
    Junit5 Annotations
    @BeforeAll @AfterAll @BeforeEach @AfterEach
    */

    //static olmali bir kere yapilacak
    @BeforeAll
    public static void setUp(){System.out.println("@BeforeAll is running");}
    @AfterAll
    public static void tearDownAll(){System.out.println("@AfterAll is running");}

    @BeforeEach
    public void setupTest(){System.out.println("@BeforeEach is runnig");}

    @AfterEach
    public void tearDownTest(){
        System.out.println("@AfterEach is running");}

    @Test
    public void test1(){
        System.out.println("test1 is runnig");
        Assertions.assertEquals(4,1+3);
    }


    @Test
    public void test2(){
    System.out.println("test2 is running");
    assertEquals(12,3*4);
    }
}



