package w03.arraylist;

public class TwoDimensionArray {
    public static void main(String[] args) {
        String[] fruits = {"apple", "orange", "banana"};
        String[] vegetables = {"potato", "onion", "carrot"};
        String[] meats = {"chicken", "pork", "beef", "fish"};

        String[][] groceries = {fruits, vegetables, meats};

        for (String[] foods: groceries) {
            // System.out.println(foods);
            for(String food: foods){
                System.out.print(food + " ");
            }
            System.out.println();
        }
    }
}