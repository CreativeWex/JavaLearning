import java.util.HashMap;
import java.util.Map;

public class Program {
    private static void displayMap(Map<Integer, String> map) {
        System.out.println("Map" + map);
        for (Map.Entry<Integer, String> element : map.entrySet()) {
            Integer key = element.getKey();
            String value = element.getValue();
            System.out.println("Ключ: " + key + "\tЗначение: " + value);
        }

    }

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");



        displayMap(map);
    }
}
