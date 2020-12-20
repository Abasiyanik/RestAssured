package pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Data
@JsonIgnoreProperties(ignoreUnknown = true)//bu olmaz ise olmaz bu tum ekstra verileri elimine eder bingo
public class ArticlePOJO {
    private String author;
    private String table;
    private String description;


}
