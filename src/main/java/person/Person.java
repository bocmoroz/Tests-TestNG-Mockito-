package person;

import java.util.ArrayList;
import java.util.List;

public class Person {

    private int id;
    private String surname;
    private String name;
    private List<Account> accounts;
    private int correctPin;

    public int getCorrectPin() {
        return correctPin;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Person(int id, String surname, String name, int correctPin) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.correctPin = correctPin;
        accounts = new ArrayList<>();
    }
}