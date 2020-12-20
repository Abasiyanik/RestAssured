package day09;


import com.github.javafaker.Faker;
import groovy.json.JsonOutput;
import org.junit.jupiter.api.RepeatedTest;
import org.w3c.dom.ls.LSOutput;


public class C3_Junit5RepeatedTest {

    @RepeatedTest(10)
public void testRepeating() {
    Faker faker = new Faker();

    System.out.println(faker.country().name());// 10 defa kullanir
        //System.out.println(faker.chuckNorris().fact());

}



}
