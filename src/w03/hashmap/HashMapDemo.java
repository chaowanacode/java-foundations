package w03.hashmap;

import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {

        HashMap<String, Double> map = new HashMap<>();

        map.put("apple", 0.50);
        map.put("orange", 0.75);
        map.put("banana", 0.25);
        map.put("coconut", 1.50);

        // map.remove("apple");
        // System.out.println(map.get("apple"));
        // System.out.println(map.containsKey("coconut"));
        // System.out.println(map.containsValue(1.50));
        // System.out.println(map.size());

        for (String key: map.keySet()) {
            System.out.println(key + " : $" + map.get(key));
        }
    }
}
