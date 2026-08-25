package w03.arraylist;

import java.util.Scanner;

public class SearchArray {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String[] fruits = {"Apple", "Orange", "Banana"};
        boolean isFound = false;
        String target;

        System.out.print("Enter a fruit to search for: ");
        target = scanner.nextLine();

        for (int i = 0; i < fruits.length; i++) {
            if (fruits[i].equals(target)){
                System.out.print("Element found at index: " + i);
                isFound = true;
                break;
            }
        }

        if (!isFound){
            System.out.print("Element not found in the array!");
        }

        scanner.close();
    }
}
