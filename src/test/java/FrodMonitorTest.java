import exceptions.StrangeOperationException;
import org.testng.Assert;
import org.testng.annotations.*;
import services.FrodMonitor;

public class FrodMonitorTest {

    private FrodMonitor frodMonitor;

    @BeforeClass
    public void setUp() {
        frodMonitor = new FrodMonitor();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After method inside " + getClass());
    }

    @AfterTest
    public void reportReady() {
        System.out.println("All tests for FrodMonitor were completed");
    }

    @Test
    public void checkFrodSuccessTest() throws StrangeOperationException {
        Assert.assertTrue(frodMonitor.check(450000));
    }

    @Test(expectedExceptions = StrangeOperationException.class)
    public void checkFrodUnsuccessTest() throws StrangeOperationException {
        frodMonitor.check(600000);
    }

}
