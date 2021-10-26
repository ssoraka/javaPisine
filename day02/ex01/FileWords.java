import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FileWords {
    private Map<String, Integer> map;

    public FileWords(String path) throws IOException {
        map = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while (br.ready()) {
                for (String s : br.readLine().trim().split("\\s+")) {
                    if (s.isEmpty()) {
                        continue;
                    }
                    if (map.containsKey(s)) {
                        map.put(s, map.get(s) + 1);
                    } else {
                        map.put(s, 1);
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Can't read file " + path);
        }
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public int[] getVector(Set<String> set) {
        int vector[] = new int[set.size()];

        int i = 0;
        for (String s : set) {
            if (map.containsKey(s)) {
                vector[i] = map.get(s);
            }
            i++;
        }
        return vector;
    }
}
