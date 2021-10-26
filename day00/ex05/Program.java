import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String names[] = new String[10];

        int i = 0;
        while (scanner.hasNext()) {
            String text = scanner.nextLine();

            if (text.equals(".")) {
                break;
            }

            if (i == 9 || text.length() > 10) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }

            names[i] = text;

            i++;
        }

        int nameCount = i;

        if (names[0] == null) {
            return;
        }


        String[] days = {" MO", " TU", " WE", " TH", " FR", " SA", " SU"};
        int arr[][] = new int[7][7];


        i = 0;
        while (scanner.hasNext()) {
            String text = null;

            if (!scanner.hasNextInt()) {
                text = scanner.nextLine();
                if (text.equals(".")) {
                    break;
                } else {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
            } else {
                if (i == 10) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }


                int num = scanner.nextInt();

                if (num > 6 || num < 1) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }


                int dayId = -1;
                text = scanner.nextLine();
                for (int j = 0; j < days.length; j++) {
                    if (days[j].equals(text)) {
                        dayId = j;
                    }
                }
                if (dayId == -1) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }

                if (arr[dayId][num] > 0) {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                }
                arr[dayId][num]++;
                arr[dayId][0]++;
            }

            i++;
        }

        int day[] = new int[30];
        int people[][][] = new int[nameCount][35][10];

        while (scanner.hasNext()) {

            String name = null;

            name = scanner.next();
            if (name.equals(".")) {
                scanner.nextLine();
                break;
            }

            int time = scanner.nextInt();
            int date = scanner.nextInt();
            day[date] = 1;

            int id = -1;
            for (int j = 0; j < nameCount; j++) {
                if (name.equals(names[j])) {
                    id = j;
                    break;
                }
            }

            if (id == -1) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }


            String text = scanner.nextLine();
            if (text.equals(" HERE")) {
                people[id][date][time] = 1;
            } else if (text.equals(" NOT_HERE")) {
                people[id][date][time] = -1;
            }

        }


        System.out.print("          ");
        for (int j = 1; j < 31; j++) {
            int dayId = j % 7;
            if (arr[dayId][0] != 0) {
                for (int k = 1; k < 7; k++) {
                    if (arr[dayId][k] != 0) {
                        System.out.printf("%d:00%s% 3d|", k, days[dayId], j);
                    }
                }
            }
        }
        System.out.println("");

        i = 0;
        while (names[i] != null) {
            int num = 10 - names[i].length();
            while (num > 0) {
                System.out.print(" ");
                num--;
            }
            System.out.print(names[i]);

            for (int j = 1; j < 31; j++) {
                int dayId = j % 7;
                if (arr[dayId][0] != 0) {
                    for (int k = 1; k < 7; k++) {
                        if (arr[dayId][k] != 0) {
                            if (people[i][j][k] == 1) {
                                System.out.print("         1|");
                            } else if (people[i][j][k] == -1) {
                                System.out.print("        -1|");
                            } else {
                                System.out.print("          |");
                            }
                        }
                    }
                }
            }

            System.out.println();
            i++;

        }
    }
}