package logic;

import java.util.Random;

public class GameLogic {
    private Random random;

    public GameLogic() {
        random = new Random();
    }

    public String getCommand(int x0, int y0, int x1, int y1) {
        if (x0 < x1) {
            return "d";
        }
        if (x0 > x1) {
            return "a";
        }
        if (y0 < y1) {
            return "s";
        }
        return "w";
    }
}
