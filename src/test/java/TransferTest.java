import exceptions.WrongSumException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import person.Account;
import person.Person;
import services.Transfer;

public class TransferTest {

    private Account sender;
    private Account recipient;
    private Transfer transfer;

    @Mock
    private Person person1;
    @Mock
    private Person person2;

    @BeforeClass
    public void setUpClass() {
        MockitoAnnotations.initMocks(this);
        sender = new Account(1, person1, 567_777_888, 666_555);
        recipient = new Account(2, person2, 999_999_999, 555_555);
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
    public void startTransferSuccessTest() throws WrongSumException {
        transfer = new Transfer(56400, sender, recipient);
        Assert.assertEquals(666_555, sender.getAmount());
        Assert.assertEquals(555_555, recipient.getAmount());
        transfer.startTransfer();
        Assert.assertEquals(610_155, sender.getAmount());
        Assert.assertEquals(611_955, recipient.getAmount());
    }

    @Test(expectedExceptions = WrongSumException.class)
    public void startTransferUnsuccessTest() throws WrongSumException {
        transfer = new Transfer(564000000, sender, recipient);
        transfer.startTransfer();
    }
}