package rush00;

import logic.GameLogic;

public class Enemy implements Characters{
    private int x;
    private int y;
    private Level level;
    private GameLogic logic;

    public Enemy(Level level, int x, int y) {
        this.x = x;
        this.y = y;
        this.level = level;
        logic = new GameLogic();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String move() {
        return logic.getCommand(x, y, level.getHero().getX(), level.getHero().getY());
    }
}
