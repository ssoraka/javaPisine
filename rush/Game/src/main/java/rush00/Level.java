package rush00;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Level {
    final static public Point LEFT = new Point(-1, 0);
    final static public Point RIGHT = new Point(1, 0);
    final static public Point UP = new Point(0, -1);
    final static public Point DOWN = new Point(0, 1);

    private int width;
    private int height;
    private int eCount;
    private int wCount;
    private int size;
    private String profile;

    private MyProperties properties;

    private char[][] map;
    private List<Enemy> enemies;
    private Hero hero;
    private Random random;
    private Point goalPoint = new Point();

    private char ENEMY;
    private char HERO;
    private char EMPTY;
    private char WALL;
    private char GOAL;

    private Map<Character, String> visualisation;


    public Level(int size, int eCount, int wCount, String profile) {
        this.width = size;
        this.height = size;
        this.eCount = eCount;
        this.wCount = wCount;
        this.profile = profile;

        initProperties(profile);

        random = new Random();
        enemies = new ArrayList<>();
        map = new char[height][width];
        initMap();
    }

    private void initProperties(String profile) {
        properties = new MyProperties(profile);

        ENEMY = properties.getChar("enemy.char");
        HERO = properties.getChar("player.char");
        EMPTY = properties.getChar("empty.char");
        WALL = properties.getChar("wall.char");
        GOAL = properties.getChar("goal.char");

        visualisation = new HashMap<>();
        visualisation.put(ENEMY, properties.getColor("enemy.color"));
        visualisation.put(HERO, properties.getColor("player.color"));
        visualisation.put(EMPTY, properties.getColor("empty.color"));
        visualisation.put(WALL, properties.getColor("wall.color"));
        visualisation.put(GOAL, properties.getColor("goal.color"));
    }

    public Hero getHero() {
        return hero;
    }

    private boolean onMap(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public void setChar(char c, int x, int y) {
        if (onMap(x, y)) {
            map[y][x] = c;
        }
    }

    public boolean isEmpty(int x, int y) {
        return onMap(x, y) && map[y][x] == EMPTY;
    }

    public boolean isGoal(int x, int y) {
        return onMap(x, y) && map[y][x] == GOAL;
    }

    public void checkWinLose(Characters e, int x, int y) {
        if (e instanceof Enemy && x == hero.getX() && y == hero.getY()) {
            setChar(EMPTY, e.getX(), e.getY());
            setChar(ENEMY, x, y);
            e.setY(y);
            e.setX(x);
            throw new LoseException();
        }
        if ((e instanceof Hero && isGoal(x, y))) {
            setChar(EMPTY, e.getX(), e.getY());
            setChar(HERO, x, y);
            e.setY(y);
            e.setX(x);
            throw new WinException();
        }
    }

    private boolean canMove(Point point) {
        return isEmpty(hero.getX() + point.x, hero.getY() + point.y)
                || isGoal(hero.getX() + point.x, hero.getY() + point.y);
    }

    public void checkSurrounding() {
        if (!canMove(UP) && !canMove(DOWN) && !canMove(RIGHT) && !canMove(LEFT)) {
            throw new LoseException();
        }
    }

    private void move(Characters e, Point p) {
        int x = e.getX();
        int y = e.getY();
        checkWinLose(e, x + p.x, y + p.y);
        char c = map[y][x];
        if (isEmpty(x + p.x, y + p.y)) {
            setChar(EMPTY, x, y);
            setChar(c, x + p.x, y + p.y);
            e.setY(y + p.y);
            e.setX(x + p.x);
        } else if (e instanceof Hero) {
            throw new CantMoveException();
        }
    }

    public void moveCharacter(Characters e, String command) {
        switch (command) {
            case "w" : move(e, UP); break;
            case "s" : move(e, DOWN); break;
            case "a" : move(e, LEFT); break;
            case "d" : move(e, RIGHT); break;
            default : break;
        }
        printMap();
    }

    public void moveAllEnemy() {
        if (profile.equals("dev")) {
            for (Enemy e : enemies) {
                System.out.println("Enter 8 for approve enemy's move form position: x=" + e.getX() + ", y=" + e.getY());
                if (isConfirm()) {
                    moveCharacter(e, e.move());
                    System.out.println("Enemy moved to position: x=" + e.getX() + ", y=" + e.getY());
                } else {
                    System.out.println("Enemy didn't move");
                }
            }
        } else {
            for (Enemy e : enemies) {
                moveCharacter(e, e.move());
            }
        }
    }

    private boolean isConfirm() {
        Scanner s = new Scanner(System.in);
        return  s.hasNext() && s.nextLine().equals("8");
    }


    private Point setObject(int count, char c) {
        int x = 0;
        int y = 0;
        while (count > 0) {
            x = random.nextInt(width);
            y = random.nextInt(height);
            if (isEmpty(x, y)) {
                setChar(c, x, y);
                count--;
                if (c == ENEMY) {
                    enemies.add(new Enemy(this, x, y));
                }
            }
        }
        Point p = new Point();
        p.x = x;
        p.y = y;
        return p;
    }

    public void initMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = EMPTY;
            }
        }
        Point p = setObject(1, GOAL);
        goalPoint.y = p.y;
        goalPoint.x = p.x;

        p = setObject(1, HERO);
        hero = new Hero(p.x, p.y);
        int minX = Math.min(goalPoint.x, hero.getX());
        int maxX = Math.max(goalPoint.x, hero.getX());
        int minY = Math.min(goalPoint.y, hero.getY());
        int maxY = Math.max(goalPoint.y, hero.getY());
        int sum = maxX - minX + 1 + maxY - minY + 1 + wCount + eCount + 2;
        if (sum >= this.width * this.height) {
            throw new LoseException();
        }
        for (int y = ++minY; y < maxY; y++) {
            map[y][minX] = 'p';
        }
        for (int x = ++minX; x < maxX; x++) {
            map[maxY][x] = 'p';
        }
        setObject(wCount, WALL);
        setObject(eCount, ENEMY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] == 'p') {
                    setChar(EMPTY, x, y);
                }
            }
        }
    }


    public void printMap() {
        if (profile.equals("production")) {
            System.out.println("\33c");
        }
        ColoredPrinter cp;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cp = new ColoredPrinter.Builder(1, false)
                        .foreground(Ansi.FColor.BLACK)
                        .background(Ansi.BColor.valueOf(visualisation.get(map[i][j])))
                        .build();
                cp.print(map[i][j]);
                cp.clear();
            }
            System.out.println();
        }
    }

    public Point getGoalPoint() {
        return goalPoint;
    }
}

class LoseException extends RuntimeException {
}

class CantMoveException extends RuntimeException {
}

class WinException extends RuntimeException {
}
