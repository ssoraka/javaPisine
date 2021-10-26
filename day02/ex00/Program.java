import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Program {
    private static final String SIGNATURES = "signatures.txt";
    private static final String RESULT = "result.txt";

    public static void main(String[] args) {
        FileQualifier fileQualifier = new FileQualifier();

        if (!fileQualifier.readFile(SIGNATURES)) {
            System.err.println("Where is no file signatures.txt");
            System.exit(-1);
        }

        while (true) {
            String path = readLine(System.in);
            if ("42".equals(path)) {
                return;
            }
            String type = fileQualifier.qualify(path);
            if (type != null) {
                System.out.println("PROCESSED");
                write(type);
            } else {
                System.err.println("FILE NOT FOUND");
            }
        }
    }

    private static void write(String text) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(RESULT, true)) {
            fileOutputStream.write(text.getBytes());
            fileOutputStream.write('\n');
        } catch (IOException e) {
            System.err.println("Can't write in file " + RESULT);
            System.exit(-1);
        }
    }

    public static String readLine(InputStream stream) {
        StringBuilder string = new StringBuilder();

        try {
            int c = 0;
            while ((c = stream.read()) != -1 && c != '\n') {
                string.append(String.format("%c", c));
            }
        } catch (IOException e) {
            System.err.println("Can't read from System.in");
            System.exit(-1);
        }
        return string.toString();
    }
}
