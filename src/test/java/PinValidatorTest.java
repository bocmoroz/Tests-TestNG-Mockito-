import exceptions.AccountLockedException;
import org.testng.Assert;
import org.testng.annotations.*;
import person.Person;
import services.PinValidator;

public class PinValidatorTest {

    private PinValidator validator;
    private Person person;

    @BeforeClass
    public void setUp() {
        person = new Person(1, "Mokeev", "Andrey", 1234);
        validator = new PinValidator();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After method inside " + getClass());
    }

    @AfterTest
    public void reportReady() {
        System.out.println("All tests for PinValidator were completed");
    }

    @Test
    public void checkPinSuccessTest() throws AccountLockedException {
        Assert.assertTrue(validator.checkPin(1234, person));
    }

    @Test(expectedExceptions = AccountLockedException.class)
    public void checkPinUnsuccessTest() throws AccountLockedException {
        for (int i = 0; i < 5; i++) {
            validator.checkPin(1230, person);
        }
    }

    @Test(expectedExceptions = AccountLockedException.class)
    public void checkPinUnsuccessTestAfter3WrongPin() throws AccountLockedException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        validator.checkPin(1234, person);
    }
}