package day10;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import lombok.Value;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ArticlePOJO;

import java.util.List;

public class C1_NewAPi_Homework {

    @DisplayName("get all articles author ifsource id is not noll ")
    @Test
    public void testGetAllArticleAuthor(){



        //JsonPath jp=
                JsonPath jp =
                given()
                        .log().uri()
                        .baseUri("http://newsapi.org")
                        .basePath("/v2")
                        .header("Authorization", "Bearer b4bdf85d74024a26b1d975b9c79b35fd ")// how to use authorization heaer wtoh token
                       // .queryParam("apiKey","d39d53da33db434791d77f7b58658007") // token varsa buna gerek kalmaz. ve direk t olarak . documenatationa bakmamk gerekir
                        //most application bear kullanilimaktadir
                        .queryParam("country","us").
                        when()
                        .get("/top-headlines").prettyPeek()
                        .jsonPath();

                List<String> allFilteredAuthors =
                jp.getList("articles.findAll{ it.source.id != null }.author"  ) ;
        System.out.println("allFilteredAuthors = " + allFilteredAuthors);
        List<String> allAuthors =
                jp.getList("articles.author"  ) ;
        System.out.println("allAuthors = " + allAuthors);

        List<String> allAuthorsWithNoNull =
                jp.getList("articles.findAll{ it.source.id != null && it.author!= null }.author"  ) ;
        System.out.println("allAuthorsWithNoNull = " + allAuthorsWithNoNull);
//        allFilteredAuthors = [Nicole Sganga, Kyle Balluck, Daniel Manzo, Newt Gingrich, Alicia Victoria Lozano, Gunjan Banerji, Michael Wursthorn, null, Fox News, Andrew Taylor, Kelsey Vlamis, Joseph Zeballos-Roig]
//        allAuthors = [Nicole Sganga, Kyle Balluck, null, Daniel Manzo, Carl O'Donnell, null, Newt Gingrich, Alicia Victoria Lozano, Michael David Smith, Gunjan Banerji, Michael Wursthorn, Mary Papenfuss, Pete Fiutak, TMZ Staff, Dave Itzkoff, null, null, Fox News, Alex Scarborough, Andrew Taylor, Kelsey Vlamis, Joseph Zeballos-Roig]
//        allAuthorsWithNoNull = [Nicole Sganga, Kyle Balluck, Daniel Manzo, Newt Gingrich, Alicia Victoria Lozano, Gunjan Banerji, Michael Wursthorn, Fox News, Andrew Taylor, Kelsey Vlamis, Joseph Zeballos-Roig]
//lets size

        System.out.println("allAuthors size= " + allAuthors.size());
        System.out.println("allFilteredAuthors size= " + allFilteredAuthors.size());
        System.out.println("allAuthorsWithNoNull size= " + allAuthorsWithNoNull.size());
//open a pojo as article pojo and get the the list of articles

       /* List<ArticlePOJO> allArticles
                =jp.getList("articles.findAll{ it.source.id != null && it.author!= null }.author",ArticlePOJO.class);
        //boylece jpath donusur
        allArticles.forEach(System.out::println);*/

        List<ArticlePOJO> allArticles
                = jp.getList("articles.findAll{ it.source.id != null && it.author!=null }",ArticlePOJO.class) ;
        allArticles.forEach(System.out::println) ;



    }


}
