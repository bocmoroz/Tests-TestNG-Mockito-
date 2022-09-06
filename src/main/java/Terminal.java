import exceptions.*;
import person.Account;

public interface Terminal {

    boolean checkMultiplicity(long sum) throws WrongSumException, WrongPinException;

    void pinCodeCheck(int pin) throws WrongPinException, AccountLockedException;

    boolean checkFrod(long sum) throws StrangeOperationException, WrongPinException;

    void transfer(long amountOfTransfer, Account account1, Account account2) throws WrongSumException, StrangeOperationException,
            WrongPinException, NoConnectionException, WrongAccountException;
}