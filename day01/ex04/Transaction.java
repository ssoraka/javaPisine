import java.util.UUID;

public class Transaction {
    enum Type {
        CREDIT("OUTCOME", ""), DEBIT("INCOME", "+");

        private String text;
        private String sign;

        Type(String text, String sign) {
            this.text = text;
            this.sign = sign;
        }

        @Override
        public String toString() {
            return text;
        }

        public String getSign() {
            return sign;
        }
    }

    private UUID identifier;
    private User recipient;
    private User sender;
    private Type transferCategory;
    private Integer amount;

    public Transaction(User recipient, User sender, Type transferCategory, Integer amount) {
        this.recipient = recipient;
        this.sender = sender;
        this.amount = amount;
        this.transferCategory = transferCategory;
        identifier = UUID.randomUUID();
    }

    public static Transaction createTransaction(User recipient, User sender, Type transferCategory, Integer amount) {
        if ((transferCategory == Type.CREDIT && amount < 0 && recipient.getBalance() >= -amount)
        || (transferCategory == Type.DEBIT && amount > 0 && sender.getBalance() >= amount)) {
            return new Transaction(recipient, sender, transferCategory, amount);
        }
        return null;
    }

    public void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
    }

    public Integer getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s, %s%s, %s, %s", sender.getName(), recipient.getName(), transferCategory.getSign(), amount, transferCategory, identifier);
    }
}
