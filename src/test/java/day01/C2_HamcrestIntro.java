package day01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/*
Hamcrest assertion Library already part of
our RestAssured Dependency is pom file
no separate dependency needed

*/
public class C2_HamcrestIntro {

    //why do we use JUnit5 is
    //RestAssure maked intelliJ a kind of Psotman
    /*

     */

    @DisplayName("Test 1+3 is 4")
    @Test
    public void test1(){
    assertThat(1+3, is(4));//Hamcrest makes it readable. daha anlamali gorunur . peah
    assertThat(1+3, equalTo(4));
    //add some nice error message if it fails
   //     assertThat("Wrong Result!!", 1+3, equalTo(5));
    // test: 1+3 is not 5
        assertThat(1+3, not (5));
        assertThat(1+3, is(not (5)));
    //test 1+3 is less than 5
    assertThat(1+3, lessThan(5));
    assertThat(1+3, greaterThan(3));

    }
    @Test
    public void testString(){
        String str = "Rest Assured is cool so far" ;
        // assert the str is "Rest Assured is cool so far"
        assertThat(str, is("Rest Assured is cool so far"));
        // assert the str is "Rest Assured IS COOL so far" in case insensitive manner
        assertThat(str, equalToIgnoringCase("Rest Assured IS COOL so far") );
        // assert the str startWith "Rest"
        assertThat(str, startsWith("Rest") );
        // assert the str endWith "so far"
        assertThat(str , endsWith("so far") );
        // assert the str contains "is cool"
        assertThat(str , containsString("is cool") );
        // assert the str contains "IS COOL" case insensitive manner
        assertThat(str, containsStringIgnoringCase("IS COOL"));
    }



}
