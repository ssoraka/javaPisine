package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi.Attribute;
import com.diogonunes.jcdp.color.api.Ansi.BColor;
import com.diogonunes.jcdp.color.api.Ansi.FColor;
import com.diogonunes.jcdp.color.api.Ansi;

public class Logic {
    BColor col;
    ColoredPrinter cp = new ColoredPrinter.Builder(1, false).build();

    public void printImage(String white, String black, URL image) {
        try {
            BufferedImage source = ImageIO.read(image);
            for (int y = 0; y < source.getHeight(); y++) {
                for (int x = 0; x < source.getWidth(); x++) {
                    Color color = new Color(source.getRGB(x, y));
                    int red = color.getRed();
                    if (red == 0) {
                        col = Ansi.BColor.valueOf(black);
                    } else {
                        col = Ansi.BColor.valueOf(white);
                    }
                    cp.print(" ", Attribute.NONE, FColor.NONE, col);
                }
                cp.println("", Attribute.NONE, FColor.NONE, BColor.NONE);
            }
        } catch (IllegalArgumentException ex) {
            cp.println("", Attribute.NONE, FColor.NONE, BColor.NONE);
            System.err.println("No valid color!");
            System.err.println("Valid color are: BLACK BLUE CYAN GREEN MAGENTA NONE RED WHITE YELLOW");
        } catch (IOException e) {
            System.err.println("Image file not found!");
            System.exit(1);
        }
    }
}