import java.net.URL;

public class Line {
    private int num;
    private URL url;

    public Line(int num, URL url) {
        this.num = num;
        this.url = url;
    }

    public int getNum() {
        return num;
    }

    public URL getUrl() {
        return url;
    }
}
