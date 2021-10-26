package printer.app;

import printer.logic.Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Program {
    private static char white;
    private static char black;
    private static URL path;

    private static final String USAGE = "Error\nRead README.txt";

    public static void main(String[] args) throws URISyntaxException {

//        new File(Program.class.getProtectionDomain().getCodeSource().getLocation()
//                .toURI()).toPath().


//        if (args.length != 3 || args[0].length() != 1 || args[1].length() != 1) {
//            System.err.println(USAGE);
//            System.exit(-1);
//        }
//        white = args[0].charAt(0);
//        black = args[1].charAt(0);
//        path = args[2];

        white = '.';
        black = '0';
        path = Program.class.getResource("/resources/it.bmp");

        BufferedImage img = null;
        try {
//            ImageIO.read(Program.class.getResource("/it.bmp"))
            img = ImageIO.read(path);
        } catch (IOException e) {
            System.err.println(USAGE);
            System.exit(-1);
        }
        Logic.convert(img, white, black);
    }
}
