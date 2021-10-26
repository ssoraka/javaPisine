public class Program {
    public static void main(String[] args) {
        User user1 = new User("Паша", 100_000);
        User user2 = new User("Миша", 50_000);
        User user3 = new User("Сережа", 200_000);

        TransactionsService service = new TransactionsService();

        service.add(user1);
        service.add(user2);
        service.add(user3);

        service.makeTransactions(user1.getIdentifier(), user2.getIdentifier(), 1000);
        service.makeTransactions(user1.getIdentifier(), user2.getIdentifier(), 1001);

        System.out.println("------------------------");
        System.out.println(service);
        System.out.println("------------------------");
        System.out.println("choose and del transaction");
        Transaction tmp = user1.getList().toArray()[0];
        System.out.println(tmp);
        System.out.println("------------------------");
        System.out.println("service now");
        service.deteleTransaction(user1.getIdentifier(), user1.getList().toArray()[0].getIdentifier().toString());
        System.out.println(service);
        System.out.println("------------------------");
        System.out.println("not valid without pair");
        for (Transaction t : service.validateTransactions()) {
            System.out.println(t);
            service.deteleTransaction(t.getSender().getIdentifier(), t.getIdentifier().toString());
        }
        System.out.println("------------------------");
        System.out.println("del not valid transactions in service");
        System.out.println(service);
        System.out.println("------------------------");
        System.out.println("calculate");
        service.retrieveBalance();
        System.out.println(service);
        System.out.println("------------------------");

        service.makeTransactions(user1.getIdentifier(), user2.getIdentifier(), 1_000_000);
    }
}
