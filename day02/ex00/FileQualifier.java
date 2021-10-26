import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileQualifier {
    private List<Line> lines;


    public FileQualifier() {
        lines = new ArrayList<>();
    }

    public boolean readFile(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            while (fileInputStream.available() != 0) {
                lines.add(readLine(fileInputStream));
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private Line readLine(FileInputStream fileInputStream) throws IOException {
        StringBuilder str = new StringBuilder();

        int chr;
        while ((chr = fileInputStream.read()) != -1 && (char) chr != '\n') {
            if (chr >= 'a' && chr <= 'f') {
                chr = chr - 'a' + 'A';
            }
            str.append((char) chr);
        }
        return new Line(str.toString());
    }


    public String qualify(String path) {
        byte bytes[] = new byte[8];

        try (FileInputStream fileInputStream = new FileInputStream(path)) {
            if (fileInputStream.read(bytes) == -1) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }

        for (Line l : lines) {
            boolean isFind = true;
            List<Byte> hex = l.getHex();
            for (int i = 0; i < hex.size(); i++) {
                if (bytes[i] != hex.get(i)) {
                    isFind = false;
                    break;
                }
            }
            if (isFind) {
                return l.getFormat();
            }
        }
        return null;
    }
}
