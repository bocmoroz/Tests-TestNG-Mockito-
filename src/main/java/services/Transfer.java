package services;

import exceptions.WrongSumException;
import person.Account;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transfer {

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private static int transferNumber;

    private final int transferId;
    private final float transferAmount;
    private final Account sender;
    private final Account recipient;
    private final Date date;


    public Transfer(float transferAmount, Account sender, Account recipient) {
        this.transferId = ++transferNumber;
        this.transferAmount = transferAmount;
        this.recipient = recipient;
        this.sender = sender;
        this.date = new Date();
    }

    private boolean isSenderHasEnoughMoney() {
        return sender.getAmount() - transferAmount >= 0;
    }

    public void startTransfer() throws WrongSumException {

        if (isSenderHasEnoughMoney()) {
            sender.setAmount(sender.getAmount() - transferAmount);
            recipient.setAmount(recipient.getAmount() + transferAmount);
        } else {
            throw new WrongSumException("Недостаточно средств для перевода");
        }

    }

    @Override
    public String toString() {
        return "№ перевода: " + transferNumber
                + "\nДата перевода: " + formatter.format(date);
    }
}