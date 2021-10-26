
public class Program {
    public static void main(String[] args) {
        int number = 479598;

        System.out.println((number % 10)
                        + (number / 10) % 10
                        + (number / 100) % 10
                        + (number / 1000) % 10
                        + (number / 10000) % 10
                        + (number / 100000) % 10
        );
    }
}
