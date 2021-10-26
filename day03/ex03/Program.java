import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Program {
    private static int count;
    private static final String URLS = "files_urls.txt";
    private static final String USAGE = "usage:\njava Program --threadsCount=[int > 0]";

    public static void main(String[] args) throws MalformedURLException {
        if (!validate(args)) {
            System.out.println(USAGE);
            System.exit(-1);
        }

        ConcurrentLinkedQueue<Line> list = new ConcurrentLinkedQueue<>(readFile(URLS));
        if (list.isEmpty()) {
            System.err.println("Where is no file files_urls.txt");
            System.exit(-1);
        }

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(new MyExecutor(list));
            thread.setName("Thread-" + (i + 1));
            thread.start();
        }
    }

    private static boolean validate(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--threadsCount=")) {
            return false;
        }
        String counts[] = args[0].split("=");
        if (counts.length != 2) {
            return false;
        }

        try {
            count = Integer.parseInt(counts[1]);
        } catch (Exception e) {
            return false;
        }
        return count > 0;
    }

    public static List<Line> readFile(String path) {
        List<Line> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while (br.ready()) {
                String[] split = br.readLine().trim().split("\\s+");
                if (split.length != 2) {
                    continue;
                }
                list.add(new Line(Integer.parseInt(split[0]), new URL(split[1])));
            }
        } catch (IOException e) {
            return list;
        }
        return list;
    }
}
