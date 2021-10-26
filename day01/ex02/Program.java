public class Program {
    public static void main(String[] args) {
        User user1 = new User("Паша", 10_000);
        User user2 = new User("Миша", 100_000);
        User user3 = new User("Сережа", -100_000);

        UsersList list = new UsersArrayList();
        list.add(user1);
        list.add(user2);
        list.add(user3);

        System.out.println(list.getByIndex(0) + " == " + user1);
        System.out.println(list.getByIndex(user2.getIdentifier()) + " == " + user2);
        System.out.println("count = " + list.getCount());

        User user4 = new User("Вася", -100_000);
        System.out.println(list.getById(3));
        System.out.println(list.getByIndex(user4.getIdentifier()));
    }
}
