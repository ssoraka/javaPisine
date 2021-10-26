import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        if (!scanner.hasNextLine()) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        char arr[] = scanner.nextLine().toCharArray();

        if (arr.length == 0) {
            System.exit(0);
        }

        int map[] = new int[66000];
        for (char c : arr) {
            map[c]++;
        }


        int indexs[] = new int[10];
        int min = 0;

        for (int i = 0; i < map.length; i++) {
            if (map[i] == 0 || map[i] < map[indexs[min]]) {
                continue;
            }

            indexs[min] = i;

            min = 0;
            for (int j = 1; j < 10 && map[indexs[min]] > 0; j++) {
                if (map[indexs[min]] >= map[indexs[j]]) {
                    min = j;
                }
            }
        }


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (map[indexs[j]] < map[indexs[j + 1]]) {
                    int tmp = indexs[j + 1];
                    indexs[j + 1] = indexs[j];
                    indexs[j] = tmp;
                }
            }
        }

        int size = 1;
        for (int i = 0; i < 10; i++) {
            if (map[indexs[i]] == 0) {
                break;
            }
            size = i + 1;
        }

        int max = map[indexs[0]];

        if (max > 999) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }

        for (int i = 11; i > 0; i--) {
            int height = (max * i);
            int heightNext = (max * (i - 1));

            for (int j = 0; j < size; j++) {
                if (map[indexs[j]] * 10 >= height) {
                    System.out.print("  #");
                } else if (map[indexs[j]] * 10 >= heightNext) {
                    System.out.printf("% 3d", map[indexs[j]]);
                } else {
                    break;
                }
            }
            System.out.println("");
        }

        for (int i = 0; i < size; i++) {
            System.out.printf("  %c", (char) indexs[i]);
        }
        System.out.println("");
    }
}