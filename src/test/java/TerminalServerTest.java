import exceptions.NoConnectionException;
import exceptions.WrongSumException;
import org.testng.Assert;
import org.testng.annotations.*;
import person.Account;
import person.Person;
import services.TerminalServer;

public class TerminalServerTest {

    private TerminalServer terminalServer;
    private Account from;
    private Account to;

    @BeforeClass
    public void setUp() {
        terminalServer = new TerminalServer();
        Person first = new Person(1, "Mokeev", "Andrey", 1234);
        Person second = new Person(2, "Ivanov", "Ivan", 5678);
        from = new Account(1, first, 567_777_889, 444_777);
        to = new Account(2, second, 789_000_000, 265_785);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After method inside " + getClass());
    }

    @AfterTest
    public void reportReady() {
        System.out.println("All tests for TerminalServer were completed");
    }

    @Test
    public void transferSuccessTest() throws WrongSumException, NoConnectionException {
        Assert.assertEquals(444_777, from.getAmount());
        Assert.assertEquals(265_785, to.getAmount());
        terminalServer.transfer(56400, from, to);
        Assert.assertEquals(388_377, from.getAmount());
        Assert.assertEquals(322_185, to.getAmount());
    }

    @Test(expectedExceptions = NoConnectionException.class)
    public void transferUnsuccessTest1() throws WrongSumException, NoConnectionException {
        terminalServer.setConnection(false);
        terminalServer.transfer(0, from, to);
    }

    @Test(expectedExceptions = WrongSumException.class)
    public void transferUnSuccessTest2() throws WrongSumException, NoConnectionException {
        terminalServer.transfer(56400000, from, to);
    }
}