package rush00;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.Scanner;

@Parameters(separators = "=")
public class Game {

    @Parameter(names = "--size", required = true)
    int size;

    @Parameter(names = "--wallsCount", required = true)
    int wCount;

    @Parameter(names = "--enemiesCount", required = true)
    int eCount;

    @Parameter(names = "--profile", required = true)
    String profile;


    public static void main(String[] args) {

        Game game = new Game();
        new JCommander(game, args);

        int sum = (game.wCount + game.eCount) * 2 + 2;
        if (sum >= game.size * game.size || game.size < 3 || game.eCount < 0 || game.wCount < 0) {
            throw new IllegalParametersException("Невозможно сгенерировать крату с такими координатами");
        }

        Level level = null;
        do {
            try {
                level = new Level(game.size, game.eCount, game.wCount, game.profile);
            } catch (LoseException e) {
                ;
            }
        } while (!mapValidation(level));
        Hero hero = level.getHero();
        System.out.println("y: "+level.getGoalPoint().y);

        level.printMap();
        try {
            level.checkSurrounding();
        } catch (LoseException e) {
            System.out.println("You won! Congratulations!");
        }



        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine().trim();
            if (command.equals("9")) {
                level.printMap();
                System.out.println("You lose!");
                break;
            }
            if (command.equals("w") || command.equals("a") || command.equals("s") || command.equals("d")) {
                try {
                    level.moveCharacter(hero, command);
                    level.moveAllEnemy();
                    level.checkSurrounding();
                    level.printMap();
                } catch (WinException e) {
                    level.printMap();
                    System.out.println("You won! Congratulations!");
                    break;
                } catch (LoseException e) {
                    level.printMap();
                    System.out.println("You lose!");
                    break;
                } catch (CantMoveException e) {
                    level.printMap();
                    System.out.println("Enter another direction!");
                }
            }
        }


    }

    private static boolean mapValidation(Level level) {
        try {
            level.checkSurrounding();
        } catch (LoseException e) {
            return false;
        }
        return true;
    }

}
