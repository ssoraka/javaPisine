package rush00;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class MyProperties {
    private static final String APP = "application-production.properties";
    private static final String[] APP_COLORS = {"empty.color", "player.color", "wall.color", "goal.color", "enemy.color"};
    private static final List<String> COLORS = Arrays.stream(
            new String[]{"BLACK", "BLUE", "CYAN", "GREEN", "MAGENTA", "NONE", "RED", "WHITE", "YELLOW"})
            .collect(Collectors.toList());

    private static final String[] CHARS = {"empty.char", "player.char", "wall.char", "goal.char", "enemy.char"};

    private Properties appProps;

    public MyProperties(String profile) {
        appProps = new Properties();
        try (InputStream inputStream = MyProperties.class.getResource("/application-" + profile + ".properties").openStream()) {
            appProps.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can't find " + APP + " for properties");
        } catch (Exception e) {
            throw new IllegalParametersException("Invalid profile");
        }
        validate();
    }

    public String getColor(String key) {
        return appProps.getProperty(key);
    }

    public char getChar(String key) {
        return appProps.getProperty(key).charAt(0);
    }

    public void printAllProperties() {
        for (String s : appProps.stringPropertyNames()) {
            System.out.println(s + " = |" + appProps.getProperty(s) + "|");
        }
    }

    private void validate() {
        for (String s : APP_COLORS) {
            if (appProps.getProperty(s) == null) {
                throw new RuntimeException("Where is no properties " + s);
            }
            if (!COLORS.contains(appProps.getProperty(s))) {
                throw new RuntimeException("Invalid color " + s);
            }
        }
        for (String s : CHARS) {
            if (appProps.getProperty(s) == null) {
                throw new RuntimeException("Where is no properties " + s);
            }
            if (appProps.getProperty(s).length() != 1) {
                throw new RuntimeException("Invalid properties " + s + " with value " + appProps.getProperty(s));
            }
        }
    }


}
