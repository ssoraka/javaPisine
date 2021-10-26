import java.util.ArrayList;
import java.util.List;

public class Line {
    private String format;
    private List<Byte> hex;

    public Line(String str) {
        String[] arr = str.split(",");

        format = arr[0];

        String[] bytes = arr[1].trim().split(" +");
        hex = new ArrayList<>();
        for (String s: bytes) {
            hex.add(getByte(s));
        }
    }

    public String getFormat() {
        return format;
    }

    public List<Byte> getHex() {
        return hex;
    }

    private Byte getByte(String s) {
        byte b = from(s.charAt(0));
        b <<= 4;
        b |= from(s.charAt(1));
        return b;
    }

    private byte from(char c) {
        if (c >= 'A' && c <= 'F') {
            return (byte)(c - 'A' + 0xA);
        } else if (c >= '0' && c <= '9'){
            return (byte)(c - '0');
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder answer = new StringBuilder(format).append(" ");

        for (Byte s: hex) {
            answer.append(String.format("%02x", s)).append(" ");
        }
        return answer.toString();
    }
}
