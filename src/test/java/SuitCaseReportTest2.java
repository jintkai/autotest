import com.autotest.controller.SuitCaseReportController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.autotest.Application.class)

//@SpringApplicationConfiguration(classes = com.autotest.Application.class )
public class SuitCaseReportTest2 {

    @Autowired
    SuitCaseReportController service;
    @org.junit.Test
    public void test(){
        System.out.println(service.getBuildReportsBySuitid(1));
    }
}
