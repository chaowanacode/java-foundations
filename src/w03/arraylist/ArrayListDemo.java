package w03.arraylist;

import java.util.Arrays;

public class ArrayListDemo {
    public static void main(String[] args) {

        String[] fruits = {"Apple","Orange","Banana","Coconut"};
        // fruits[0] = "Pineapple";
        int numOfFruits = fruits.length;
        Arrays.sort(fruits);
        Arrays.fill(fruits, "Pineapple");

        System.out.println(fruits[3]);
        System.out.println(numOfFruits);

        for (int i = 0; i < fruits.length; i++) {
            System.out.println(fruits[i]);
        }

        for(String fruit: fruits){
            System.out.println(fruit);
        }
    }
}
