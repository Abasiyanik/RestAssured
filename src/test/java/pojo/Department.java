package pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Data
@JsonIgnoreProperties(ignoreUnknown = true)//bu olmaz ise olmaz bu tum ekstra verileri elimine eder bingo
public class Department {

    private int department_id ;
    private String department_name ;
    private int manager_id ;
    private int location_id ;


}