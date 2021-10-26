public class Program implements Runnable{
    private int count;
    private String message;

    private static int cycleCount;
    private static final String USAGE = "usage:\njava Program --count=[int > 0]";

    public static void main(String[] args) throws InterruptedException {

        if (!validate(args)) {
            System.out.println(USAGE);
            System.exit(-1);
        }

        Thread thread1 = new Thread(new Program(cycleCount, "Egg"));
        Thread thread2 = new Thread(new Program(cycleCount, "Hen"));

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        new Program(cycleCount,"Human").run();
    }

    private static boolean validate(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--count=")) {
            return false;
        }
        String[] counts = args[0].split("=");
        if (counts.length != 2) {
            return false;
        }
        try {
            cycleCount = Integer.parseInt(counts[1]);
        } catch (Exception e) {
            return false;
        }
        return cycleCount > 0;
    }


    public Program(int count, String message) {
        this.count = count;
        this.message = message;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
    }
}
