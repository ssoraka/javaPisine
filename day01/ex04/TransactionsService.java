class IllegalTransactionException extends RuntimeException {}

public class TransactionsService {
    private UsersList list;

    public TransactionsService() {
        list = new UsersArrayList();
    }

    public void add(User user) {
        list.add(user);
    }

    private void retrieveBalance(User user) {
        Transaction[] transactions = getTransactions(user);

        Integer balance = user.getBalance();
        for (Transaction t : transactions) {
            balance -= t.getAmount();
        }
        user.setBalance(balance);
    }

    public void retrieveBalance() {
        for (int i = 0; i < list.getCount(); i++) {
            retrieveBalance(list.getByIndex(i));
        }
    }

    public void makeTransactions(Integer senderId, Integer recipientId, Integer amount) {
        User sender = list.getById(senderId);
        User recipient = list.getById(recipientId);

        if (amount > 0 && sender.getBalance() < amount || amount < 0 && recipient.getBalance() < -amount) {
            throw new IllegalTransactionException();
        }

        if (amount > 0) {
            Transaction transaction1 = Transaction.createTransaction(recipient, sender, Transaction.Type.DEBIT, amount);
            Transaction transaction2 = Transaction.createTransaction(sender, recipient, Transaction.Type.CREDIT, -amount);
            transaction2.setIdentifier(transaction1.getIdentifier());
            sender.addTransaction(transaction1);
            recipient.addTransaction(transaction2);
        } else if (amount < 0) {
            Transaction transaction1 = Transaction.createTransaction(sender, recipient, Transaction.Type.DEBIT, -amount);
            Transaction transaction2 = Transaction.createTransaction(recipient, sender, Transaction.Type.CREDIT, amount);
            transaction2.setIdentifier(transaction1.getIdentifier());
            recipient.addTransaction(transaction1);
            sender.addTransaction(transaction2);
        }
    }

    public Transaction[] getTransactions(User user) {
        return user.getList().toArray();
    }

    public void deteleTransaction(Integer userId, String UUID) {
        User user = list.getById(userId);
        user.getList().remove(UUID);
    }

    public Transaction[] validateTransactions() {
        TransactionsList notValid = new TransactionsLinkedList();

        for (int i = 0; i < list.getCount(); i++) {
            Transaction[] transactions = this.list.getByIndex(i).getList().toArray();
            for (Transaction t : transactions) {
                boolean valid = false;
                for (Transaction t2 : t.getRecipient().getList().toArray()) {
                    if (t.getIdentifier() == t2.getIdentifier()) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    notValid.add(t);
                }
            }
        }
        return notValid.toArray();
    }

    @Override
    public String toString() {
        String text = list.getCount().toString() + " users\n";
        for (int i = 0; i < list.getCount(); i++) {
            User user = list.getByIndex(i);
            text += user.toString() + "\n";
            for (Transaction t : user.getList().toArray()) {
                text += t.toString();
                text += "\n";
            }
        }
        return text;
    }
}
