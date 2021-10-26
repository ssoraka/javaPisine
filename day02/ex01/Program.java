import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Program {
    private static final String DICTIONARY = "dictionary.txt";
    private static Set<String> set = new HashSet<>();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("need 2 files");
            System.exit(-1);
        }

        FileWords file1 = null;
        FileWords file2 = null;
        try {
            file1 = new FileWords(args[0]);
            file2 = new FileWords(args[1]);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }


        set.addAll(file1.getMap().keySet());
        set.addAll(file2.getMap().keySet());

        int v1[] = file1.getVector(set);
        int v2[] = file2.getVector(set);

        double similarity = multy(v1, v2) / (vectorLen(v1) * vectorLen(v2));

        System.out.printf("Similarity = %.2f", similarity);
        write(DICTIONARY);
    }

    private static double multy(int v1[], int v2[]) {
        double answer = 0;
        for (int i = 0; i < v1.length; i++) {
            answer += v1[i] * v2[i];
        }
        return answer;
    }

    private static double vectorLen(int v[]) {
        double len = 0;
        for (int i = 0; i < v.length; i++) {
            len += (double)v[i] * v[i];
        }
        return Math.sqrt(len);
    }

    private static void write(String path) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path))) {
            for (String s : set) {
                br.write(s);
                br.write('\n');
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
