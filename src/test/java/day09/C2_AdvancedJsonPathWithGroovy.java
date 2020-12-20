package day09;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Department;
import testbase.HR_ORDS_TestBase;

import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class C2_AdvancedJsonPathWithGroovy extends HR_ORDS_TestBase{

    @Test
    public void testDepartmentPOJO(){
        Department d = new Department();
        d.setDepartment_id(100);
        System.out.println(d.getDepartment_id());
        //System.out.println( d.getDepartment_id() );

        Department d2
                = new Department(100,"ABC",12,1700);
        System.out.println("d2 = " + d2);
//lombok sayesinde getter ve stter i uzun uzun a yazmaya gerek kalmaz hatta contrcutori bile
        //https://projectlombok.org/features/all
        // ;ombok tag ile bize bumlari bize saglar

    }

    @DisplayName("GET /departments and save List of POJO")
    @Test
    public void testDepartmentJsonArrayToListOfPojo(){

        List<Department> allDeps = get("/departments")
                .jsonPath().getList("items", Department.class) ;
        //allDeps.forEach(System.out::println);


        // COPY THE CONTENT OF THIS LIST INTO NEW LIST
        // AND ONLY PRINT IF THE DEP MANAGER ID IS NOT NULL
        //amac employ olmayanlari ayirip listeyi kisaltmak
        List<Department> allDepsCopy = new ArrayList<>(allDeps);//birda listeyi baska listeye aktardik
        //ve yeni listeyi boylece ekledik
        allDepsCopy.removeIf( eachDep -> eachDep.getManager_id()==0 ) ;
        allDepsCopy.forEach(System.out::println);

    }
    @DisplayName("GET /departments and filter the result with JsonPath groovy")
    @Test
    public void testFilterResultWithGroovy(){//groovy dilini kullanarak verileri basta filtere edebilir iz

        //ayrica foreach de oldugu gibi burda da findAll kullaniyoruz ama it onemli it saysenide herseyi it e equilibre ederiz.

        JsonPath jp = get("/departments").jsonPath();
        List<Department> allDeps =
                jp.getList("items.findAll { it.manager_id > 0 }" , Department.class ) ;
        //items comees from jsonPAth and each repersended by"it"  and manager.id>0 means anything bigger than 0 show up

        allDeps.forEach(System.out::println);
        // what if I just wanted to get List<String> to store DepartmentName

        List<String> depNames = jp.getList("items.department_name") ;
        System.out.println("depNames = " + depNames);
        // -->> items.department_name (all)
        // -->> items.findAll {it.manager_id>0 }.department_name (filtered for manager_id more than 0)
        List<String> depNamesFiltered = jp.getList("items.findAll {it.manager_id>0 }.department_name") ;
        System.out.println("depNamesFiltered = " + depNamesFiltered);

        //Get all departments Id if its more than 70
        List <Integer> allDepIds=jp.getList("items.department_id");
        System.out.println("allDepIds = " + allDepIds);//allDepIds = [10, 17, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240]

        List <Integer> allDepIdFiltered=jp.getList("items.department_id.findAll{it>70}");
        //it refers t0 items.department.id and it sohould be more than 70
        System.out.println("allDepIdFiltered = " + allDepIdFiltered);//allDepIdFiltered = [80, 90, 100, 110, 120, 130, 140, 150, 160, 170, 180, 190, 200, 210, 220, 230, 240]

        // what if we have more than one condition for example : department_id between 70 - 100
        List<Integer> deps70to100 =
                jp.getList("items.department_id.findAll{ it >= 70 && it<=100  }") ;
        System.out.println("deps70to100 = " + deps70to100);//deps70to100 = [70, 80, 90, 100]
// get the name of departments if department_id between 70 - 100


        List<String>allDeps70to100=
                jp.getList("items.findAll{it.department_id >=70 && it.department_id <=100}.department_name");//burdda 70-100 dep id olanlarin departmen_name verilir
        System.out.println("allDeps70to100 = " + allDeps70to100);//allDeps70to100 = [Public Relations, Sales, Executive, Finance]


//        There are other interesting methods that we can use on collections in Groovy as well, for example:
//        find – finds the first item matching a closure predicate
//        collect – collect the return value of calling a closure on each item in a collection
//        sum – Sum all the items in the collection
//        max/min – returns the max/min values of the collection

        // findAll-->> will return all matching result
// find -->> will return first match for the condition
        System.out.println("=====================================");
        String dep10 =  jp.getString("items.find{ it.department_id==10 }.department_name");
        System.out.println("department 10 name = " + dep10);
//department 10 name = Administration

        //use : sum / min / max/
        //target department id
        int sum=jp.getInt("items.sum{ it.department_id }");
        int sum1=jp.getInt("items.department_id.sum()");

        System.out.println("sum = " + sum);
        System.out.println("sum1 = " + sum1);

//        int max1=jp.getInt("items.max{ it.department_id }");//bu calismiyor
//        System.out.println("max = " + max1);

        int min=jp.getInt("items.department_id.min()");
        int max=jp.getInt("items.department_id.max()");
        System.out.println("max = " + max);
        System.out.println("min = " + min);


        // print number 5 dep ID
        System.out.println("number 5 dep ID" + jp.getInt("items.department_id[4]")   );
        // print number last dep ID
        System.out.println("last dep ID " + jp.getInt("items.department_id[-1]")   );
        // print from index 7 till index 10 dep ID
        System.out.println("index 7-10 dep ID " + jp.getList("items.department_id[7..10]"));




    }
}
