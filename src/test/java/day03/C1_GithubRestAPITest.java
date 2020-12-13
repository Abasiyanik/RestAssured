package day03;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class C1_GithubRestAPITest {

    //create a test for testing github rest api users/user endpoint https://api.github.com/users/Abasiyanik

    @DisplayName("test github get /users/{username}")
    @Test
    public void testGitHub() {

       given()
               .pathParam("kullaniciadi", "Abasiyanik").
       when()
               .get("https://api.github.com/users/{kullaniciadi}").
        then()
               .assertThat()//bu useless isa yaramaz sadece okunusu kolaylastirir
               .statusCode(is(200))
               .contentType(ContentType.JSON)
               .header("server", "GitHub.com")
               .body("login", is("Abasiyanik"))
       ;



    }
}
