package day01;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;//bunu ekleyerek assertions.assertEquals daki birinci sinif gider yuha


@DisplayName("Day 1 Hello Test")
public class C1_HelloTest {
    /*
    Junit5 Annotations
    @BeforeAll @AfterAll @BeforeEach @AfterEach

    @DispayName

    @Displayed
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

    @DisplayName("Test1:1+3=4")
    @Test
    public void test1(){
        System.out.println("test1 is runnig");
        Assertions.assertEquals(4,1+3);
    }

    @DisplayName("Test2:3+4=12")
    @Test
    public void test2(){
    System.out.println("test2 is running");
    assertEquals(12,3*4);
    }

    @Disabled// it ignores if the test failed
    @DisplayName("Test3:2+5=10")
    @Test
    public void test3(){
        System.out.println("test3 is running");
        assertEquals(10,3*4);
    }

}



