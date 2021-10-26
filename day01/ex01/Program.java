public class Program {
    public static void main(String[] args) {
        User user1 = new User("Паша", 10_000);
        User user2 = new User("Миша", 100_000);
        User user3 = new User("Сережа", -100_000);

        System.out.println(user1.getIdentifier());
        System.out.println(user2.getIdentifier());
        System.out.println(user3.getIdentifier());
    }
}
