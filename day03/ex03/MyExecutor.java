import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyExecutor implements Runnable {
    private ConcurrentLinkedQueue<Line> list;

    public MyExecutor(ConcurrentLinkedQueue<Line> list) {
        this.list = list;
    }

    public boolean download(URL url) {
        String[] split = url.toString().split("/");
        String name = split[split.length - 1];


        try (InputStream in = url.openStream();
             FileOutputStream fos = new FileOutputStream(name)) {
            ReadableByteChannel rbc = Channels.newChannel(in);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        while (true) {
            Line line = null;
            try {
                line = list.remove();
            } catch (Exception e) {
                return;
            }

            System.out.println(Thread.currentThread().getName() + " start download file number " + line.getNum());
            if (download(line.getUrl())) {
                System.out.println(Thread.currentThread().getName() + " finish download file number " + line.getNum());
            } else {
                System.err.println(Thread.currentThread().getName() + " can't download file number " + line.getNum());
            }
        }
    }
}
