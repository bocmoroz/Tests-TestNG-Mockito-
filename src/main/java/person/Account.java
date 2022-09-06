package person;

public class Account {

    private int id;
    private Person owner;
    private long accountNumber;
    private float amount;

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Account(int id, Person owner, long accountNumber, float amount) {
        this.id = id;
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.amount = amount;
        owner.addAccount(this);
    }
}