package printer.logic;

import java.awt.image.BufferedImage;

public class Logic {
    private static final int WHITE = -1;

    public static void convert(BufferedImage image, char whileSymb, char blackSymb) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getRGB(j, i) == WHITE) {
                    System.out.print(whileSymb);
                } else {
                    System.out.print(blackSymb);
                }
            }
            System.out.println();
        }
    }
}
