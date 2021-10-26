import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Program {
    private static long size = 50;
    private static long count = 3;
    private static int arr[];

    private static final String USAGE = "usage:\njava Program --arraySize=[int > 0] --threadsCount=[int > 0]\n2_000_000 >= arraySize >= threadsCount";

    public static void main(String[] args) {
        if (!validate(args)) {
            System.out.println(USAGE);
            System.exit(-1);
        }
        arr = randomArr();

        Calculator calc = new Calculator(arr, 0, (int) size - 1);
        System.out.println("Sum: " + calc.calc());

        List<Calculator> calculators = createThreads();

        try {
            System.out.println("Sum by threads: " + executeByThreads(calculators));
        } catch (Exception e) {
            System.err.println("Some errors");
        }
    }

    private static int[] randomArr() {
        arr = new int[(int) size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(1000);
        }
        return arr;
    }

    private static List<Calculator> createThreads() {
        List<Calculator> calculators = new ArrayList<>((int) size);
        for (int i = 0; i < count; i++) {
            int start = (int) ((size * i) / count);
            int end = (int) Math.min((size * (i + 1)) / count, size) - 1;
            Calculator calculator = new Calculator(arr, start, end);
            calculators.add(calculator);
        }
        return calculators;
    }

    private static long executeByThreads(List<Calculator> calculators) throws InterruptedException {
        for (Calculator c : calculators) {
            c.start();
        }

        long answer = 0;
        for (Calculator c : calculators) {
            c.join();
            answer += c.getSum();
        }
        return answer;
    }

    private static long executeByPool(List<Calculator> calculators) throws InterruptedException, ExecutionException {
        long answer = 0;

        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            List<Future<Calculator>> futures = executorService.invokeAll(calculators);
            for (Future<Calculator> f : futures) {
                answer += f.get().getSum();
            }
        } finally {
            executorService.shutdown();
        }
        return answer;
    }

    private static boolean validate(String[] args) {
        if (args.length != 2 || !args[0].startsWith("--arraySize=") || !args[1].startsWith("--threadsCount=")) {
            return false;
        }
        String sizes[] = args[0].split("=");
        String counts[] = args[1].split("=");
        if (counts.length != 2 || sizes.length != 2) {
            return false;
        }

        try {
            count = Integer.parseInt(counts[1]);
            size = Integer.parseInt(sizes[1]);
        } catch (Exception e) {
            return false;
        }
        return (count > 0 && size > 0 && count <= size && size <= 2_000_000);
    }
}
