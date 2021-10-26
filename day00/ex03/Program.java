import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long digits = 0;
        int count = 0;
        while (scanner.hasNextLine()) {

            String text = scanner.next();

            if (text.equals("42")) {
                break;
            }

            if (!"Week".equals(text) || !scanner.hasNextInt()) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }

            if (count == 18 || count + 1 != scanner.nextInt()) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }

            if (!"".equals(scanner.nextLine())) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }

            int min = 9;
            int i = 0;
            for (i = 0; i < 5; i++) {
                if (!scanner.hasNextInt()) {
                    break;
                }
                int num = scanner.nextInt();

                if (num > 9 || num < 1) {
                    break;
                }
                if (num < min) {
                    min = num;
                }
            }

            if (i != 5 || !"".equals(scanner.nextLine())) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }

            long value = min;
            for (int j = 0; j < count; j++) {
                value *= 10;
            }

            digits += value;
            count++;
        }

        int i = 0;
        while (digits != 0) {
            System.out.print("Week ");
            System.out.print(i + 1);
            System.out.print(" ");

            int digit = (int)(digits % 10);
            for (int j = 0; j < digit; j++) {
                System.out.print("=");
            }
            System.out.println(">");

            i++;
            digits /= 10;
        }
    }
}
