public class Program {
    public static void main(String[] args) {
        User user1 = new User("John", 10_000);
        user1.setIdentifier(1);
        User user2 = new User("Mike", 100_000);
        user2.setIdentifier(2);

        Transaction transaction1 = Transaction.createTransaction(user2, user1, Transaction.Type.CREDIT, -500);
        Transaction transaction2 = Transaction.createTransaction(user1, user2, Transaction.Type.DEBIT, 500);

        System.out.println(transaction1);
        System.out.println(transaction2);

        user1.setBalance(user1.getBalance() - 500);
        user2.setBalance(user2.getBalance() + 500);

        System.out.printf("%s %d\n", user1.getName(), user1.getBalance());
        System.out.printf("%s %d\n", user2.getName(), user2.getBalance());


        transaction1 = Transaction.createTransaction(user1, user2, Transaction.Type.DEBIT, -5_000);
        System.out.println(transaction1);

        transaction1 = Transaction.createTransaction(user1, user2, Transaction.Type.DEBIT, 500_000);
        System.out.println(transaction1);


    }
}
