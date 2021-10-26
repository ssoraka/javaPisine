import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Program {
    private static Directory dir;

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder")) {
            System.out.println("usage:\njava Program --current-folder=[PATH]");
            System.exit(-1);
        }

        String split[] = args[0].split("=");
        dir = new Directory();

        if (split.length != 2 || !dir.isCurrentPath(split[1])) {
            System.out.println("usage:\njava Program --current-folder=[PATH]");
            System.exit(-1);
        }
        dir.cd(split[1]);


        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                applyCommand(br.readLine().trim());
            }
        } catch (IOException e) {
            System.out.println("Can't read from System.in");
            System.exit(-1);
        }
    }

    private static void applyCommand(String line) {
        String text[] = line.split(" +");

        switch (text[0]) {
            case "ls": {
                if (text.length == 1) {
                    dir.ls();
                } else if (text.length == 2) {
                    dir.ls(text[1]);
                } else {
                    System.err.println("To many arguments");
                }
                break;
            }
            case "cd": {
                if (text.length == 1) {
                    return;
                } else if (text.length == 2) {
                    dir.cd(text[1]);
                } else {
                    System.err.println("To many arguments");
                }
                break;
            }
            case "mv": {
                if (text.length != 3) {
                    System.err.println("Need 2 arguments");
                } else {
                    dir.mv(text[1], text[2]);
                }
                break;
            }
            case "exit":
                System.exit(0);
            default: {
                System.err.println("unknown command");
                break;
            }
        }
    }
}
