package day05;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.MethodOrderer.*;// bunu static olarak import edersek sadece digerini yazarak olayi cozeriz
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)//this is necessary for ordering test methods based upon @Order() tag

//thse are all available optionsfor ordering test
//Based on @Order()
@TestMethodOrder(OrderAnnotation.class)//this is necessary for ordering test methods based upon @Order() tag

//Random
//@TestMethodOrder(Random.class)// to randimize the test
//default, based on method name
//@TestMethodOrder(MethodName.class)//order based on methodname
//@TestMethodOrder(MethodOrderer.DisplayName.class)//order based on methodname
public class C4_TestOrderingInJunit5 {


    @Test
    @DisplayName("A Method")
    @Order(3)
    public void testA(){
        System.out.println("runnnig test t");
    }

    @Test
    @Order(5)
    @DisplayName("b Method")
    public void testD(){
        System.out.println("runnnig test h");
    }

    @Test
    @Order(2)
    @DisplayName("c Method")
    public void testC(){
        System.out.println("runnnig test a");
    }

    @Test
    @Order(4)
    @DisplayName("d Method")
    public void testB(){
        System.out.println("runnnig test i");
    }
    @Test
    @Order(1)
    @DisplayName("e Method")
    public void testf(){
        System.out.println("runnnig test f");
    }


}
