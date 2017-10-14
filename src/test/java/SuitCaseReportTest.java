import com.autotest.controller.SuitCaseReportController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(classes = com.autotest.Application.class)
public class SuitCaseReportTest extends AbstractTestNGSpringContextTests {

    @Autowired
    SuitCaseReportController service;
    @Test
    public void test(){

        System.out.println(service.getSuitCaseBuildReport(1));

    }
}
