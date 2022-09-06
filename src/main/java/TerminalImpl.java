import exceptions.*;
import person.Account;
import person.Person;
import services.*;

public class TerminalImpl implements Terminal {

    private final PinValidator pinValidator;
    private final FrodMonitor frodMonitor;
    private final TerminalServer server;
    private final Person person;
    private boolean rightPin;

    public TerminalImpl(Person person, PinValidator pinValidator, FrodMonitor frodMonitor, TerminalServer server) {
        this.person = person;
        this.pinValidator = pinValidator;
        this.frodMonitor = frodMonitor;
        this.server = server;
    }

    public boolean isRightPin() {
        return rightPin;
    }

    @Override
    public void pinCodeCheck(int pin) throws WrongPinException, AccountLockedException {

        if (pinValidator.checkPin(pin, person)) {
            rightPin = true;
        } else {
            rightPin = false;
            throw new WrongPinException("Неправильный ПИН-код. Попробуйте заново");
        }
    }

    @Override
    public boolean checkMultiplicity(long sum) throws WrongSumException, WrongPinException {

        if (!rightPin) {
            throw new WrongPinException("Необходимо ввести ПИН-код");
        }

        if (sum % 100 == 0) {
            return true;
        } else {
            throw new WrongSumException("Терминал может переводить только суммы кратные 100");
        }
    }

    @Override
    public boolean checkFrod(long sum) throws StrangeOperationException, WrongPinException {

        if (!rightPin) {
            throw new WrongPinException("Необходимо ввести ПИН-код");
        }

        return frodMonitor.check(sum);
    }

    @Override
    public void transfer(long amountOfTransfer, Account accountFrom, Account accountTo) throws WrongSumException,
            StrangeOperationException, WrongPinException, NoConnectionException, WrongAccountException {

        if (!rightPin) {
            throw new WrongPinException("Необходимо ввести ПИН-код");
        }

        if (!person.getAccounts().contains(accountFrom)) {
            throw new WrongAccountException("Данный счёт вам не принадлежит. Перевод выполнить невозможно");
        }

        if (checkMultiplicity(amountOfTransfer) && checkFrod(amountOfTransfer)) {
            server.transfer(amountOfTransfer, accountFrom, accountTo);
        }

        System.out.println("Перевод выполнен");

    }

}