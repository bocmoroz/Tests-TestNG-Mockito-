import exceptions.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.*;
import person.Account;
import person.Person;
import services.FrodMonitor;
import services.PinValidator;
import services.TerminalServer;

public class TerminalImplTest {

    private TerminalImpl terminalImpl;
    private Person person1;
    private Account account1;
    private Person person2;
    private Account account2;
    private final FrodMonitor frodMonitor = new FrodMonitor();
    @Mock
    private PinValidator pinValidator;

    @Mock
    private TerminalServer server;

    @BeforeClass
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
        person1 = new Person(1, "Mokeev", "Andrey", 1234);
        account1 = new Account(1, person1, 567_777_888, 666_555);
        person2 = new Person(2, "Ivanov", "Ivan", 1235);
        account2 = new Account(2, person2, 999_999_999, 555_555);
        terminalImpl = new TerminalImpl(person1, pinValidator, frodMonitor, server);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After method inside " + getClass());
    }

    @AfterTest
    public void reportReady() {
        System.out.println("All tests for TerminalImpl were completed");
    }

    @Test
    public void pinCodeCheckSuccessTest() throws WrongPinException, AccountLockedException {
        Assert.assertFalse(terminalImpl.isRightPin());
        Mockito.when(pinValidator.checkPin(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        terminalImpl.pinCodeCheck(1230);
        Assert.assertTrue(terminalImpl.isRightPin());
    }

    @Test(expectedExceptions = WrongPinException.class)
    public void pinCodeCheckUnsuccessTest() throws WrongPinException, AccountLockedException {
        Mockito.when(pinValidator.checkPin(Mockito.anyInt(), Mockito.any())).thenReturn(false);
        terminalImpl.pinCodeCheck(1234);

    }

    @Test(dependsOnMethods = {"pinCodeCheckSuccessTest"})
    public void transferSuccessTest() throws WrongPinException, WrongSumException, NoConnectionException,
            StrangeOperationException, WrongAccountException, AccountLockedException {
        Mockito.when(pinValidator.checkPin(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        terminalImpl.pinCodeCheck(1230);
        Mockito.doNothing().when(server).transfer(Mockito.anyLong(), Mockito.any(), Mockito.any());
        terminalImpl.transfer(56400, account1, Mockito.mock(Account.class));
    }

    @Test(dependsOnMethods = {"pinCodeCheckSuccessTest"}, expectedExceptions = WrongAccountException.class)
    public void transferUnsuccessTest() throws WrongPinException, WrongSumException, NoConnectionException,
            StrangeOperationException, WrongAccountException, AccountLockedException {
        Mockito.when(pinValidator.checkPin(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        terminalImpl.pinCodeCheck(1230);
        Mockito.doNothing().when(server).transfer(Mockito.anyLong(), Mockito.any(), Mockito.any());
        terminalImpl.transfer(56400, account2, Mockito.mock(Account.class));
    }

    @Test(dependsOnMethods = {"pinCodeCheckSuccessTest"}, expectedExceptions = WrongSumException.class)
    public void checkMultiplicityUnsuccessTest() throws AccountLockedException, WrongPinException, WrongSumException {
        Mockito.when(pinValidator.checkPin(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        terminalImpl.pinCodeCheck(1230);
        terminalImpl.checkMultiplicity(1001);
    }

    @Test(dependsOnMethods = {"pinCodeCheckSuccessTest"}, expectedExceptions = StrangeOperationException.class)
    public void checkFrodUnsuccessTest() throws AccountLockedException, StrangeOperationException, WrongPinException {
        Mockito.when(pinValidator.checkPin(Mockito.anyInt(), Mockito.any())).thenReturn(true);
        terminalImpl.pinCodeCheck(1230);
        terminalImpl.checkFrod(50000000);
    }

}