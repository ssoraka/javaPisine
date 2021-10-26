import java.util.concurrent.Callable;

public class Calculator extends Thread implements Callable<Calculator> {

    private int arr[];
    private int id;
    private int start;
    private int end;
    private long sum;

    private static int lastId = 0;

    public Calculator(int arr[], int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
        id = lastId++;
    }

    public long getSum() {
        return sum;
    }

    public long calc() {
        sum = 0;
        for (int i = start; i <= end; i++) {
            sum += arr[i];
        }
        return sum;
    }

    @Override
    public void run() {
        call();
    }

    @Override
    public Calculator call() {
        calc();
        System.out.println(toString());
        return this;
    }

    @Override
    public String toString() {
        return getName() + "Thread " + id + ": from " + start + " to " + end + " sum is " + sum;
    }
}
