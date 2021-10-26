package edu.school21.printer.app;

import edu.school21.printer.logic.Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Program {
    private static char white;
    private static char black;
    private static String path;

    private static final String USAGE = "Error\nRead README.txt";

    public static void main(String[] args) {
        if (args.length != 3 || args[0].length() != 1 || args[1].length() != 1) {
            System.err.println(USAGE);
            System.exit(-1);
        }
        white = args[0].charAt(0);
        black = args[1].charAt(0);
        path = args[2];

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println(USAGE);
            System.exit(-1);
        }
        Logic.convert(img, white, black);
    }
}
