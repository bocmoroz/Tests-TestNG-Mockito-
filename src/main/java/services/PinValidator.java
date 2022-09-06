package services;

import exceptions.AccountLockedException;
import person.Person;

public class PinValidator {

    private int countTries;
    private long timeForBlock;

    public boolean checkPin(int pin, Person person) throws AccountLockedException {
        countTries++;

        if (countTries > 3) {
            countTries = 0;
            timeForBlock = System.currentTimeMillis();
            throw new AccountLockedException("Слишком много неправильных попыток ввода. Попробуйте через 5 секунд");
        }

        if (System.currentTimeMillis() < (timeForBlock + 5000)) {
            countTries = 0;
            throw new AccountLockedException("5 секунд ещё не прошло");
        }

        if (pin == person.getCorrectPin()) {
            countTries = 0;
            return true;
        }

        return false;
    }
}