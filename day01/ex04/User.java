public class User {
    private Integer identifier;
    private String name;
    private Integer balance;
    private TransactionsList list;

    public User(String name, Integer balance) {
        this.name = name;
        if (balance > 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
        identifier = UserIdsGenerator.getInstance().generateId();
        list = new TransactionsLinkedList();
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        list.add(transaction);
    }

    public TransactionsList getList() {
        return list;
    }

    @Override
    public String toString() {
        return name + "(" + identifier + ") balance" + balance;
    }
}
