import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        if (!scanner.hasNextInt()) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        int num = scanner.nextInt();
        if (num <= 1) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        boolean isPrime = true;
        int i;
        for (i = 2; i * i <= num && isPrime; i++) {
            if (num % i == 0) {
                isPrime = false;
            }
        }
        System.out.print(isPrime);
        System.out.print(' ');
        System.out.println(i - 1);
    }
}