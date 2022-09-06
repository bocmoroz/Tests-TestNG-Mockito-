package services;

import exceptions.NoConnectionException;
import exceptions.WrongSumException;
import person.Account;

public class TerminalServer {

    private boolean connection = true;

    public void setConnection(boolean connection) {
        this.connection = connection;
    }

    public void transfer(float amount, Account accountFrom, Account accountTo) throws WrongSumException, NoConnectionException {

        if (!connection) {
            throw new NoConnectionException("Отсутствует соединение");
        }

        Transfer newTrasfer = new Transfer(amount, accountFrom, accountTo);
        newTrasfer.startTransfer();
        System.out.println(newTrasfer.toString());

    }
}