import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Directory {
    private String path;
    private File file;

    public Directory() {
        this.path = "";
    }

    public void ls() {
        ls(path);
    }

    public void ls(String path) {
        File file = new File(getPath(path));
        if (!file.exists()) {
            return;
        }
        for (File f : listFiles(file)) {
            System.out.printf("%s %dKB\n", f.getName(), getSize(f) / 1024);
        }
    }

    public boolean isCurrentPath(String path) {
        File file = new File(getPath(path));
        if (file.exists() && file.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    public void cd(String next) {
        if (next.isEmpty()) {
            return;
        }

        File file = new File(getPath(next));
        if (file.exists() && file.isDirectory()) {
            path = file.toPath().normalize().toString();
            this.file = file;
            System.out.println(path);
        } else {
            System.err.println("No such directory");
        }
    }

    private String getPath(String name) {
        if (name.startsWith("/")) {
            return name;
        } else if (name.startsWith("./")) {
            return path + name.substring(1);
        }
        return path + "/" + name;
    }

    public void mv(String from, String to) {
        if (from.equals(to)) {
            return;
        }

        from = getPath(from);
        to = getPath(to);

        File file = new File(from);
        File fileTo = new File(to);
        if (!file.exists()) {
            System.err.println("where is no such file" + from);
            return;
        }
        if (fileTo.exists() && fileTo.isDirectory()) {
            fileTo = new File(fileTo.getAbsolutePath() + "/" + file.getName());
        }
        try {
            Files.move(file.toPath(), fileTo.toPath());
        } catch (IOException e) {
            System.err.println("where is no such directory");
        }
    }

    private File[] listFiles(File file) {
        File files[] = file.listFiles();
        if (files == null) {
            return new File[0];
        }
        return files;
    }

    private long getSize(File file) {
        if (!file.isDirectory()) {
            return file.length();
        }
        long size = 0;
        for (File f : listFiles(file)) {
            size += getSize(f);
        }
        return size;
    }
}
