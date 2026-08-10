package w02.basics;

public class Variables {
    public static void main(String[] args) {
        System.out.println("Variables");

        // Primitive
        int age = 30;
        int year = 2026;

        double price = 99.99;
        double priceOne = 99;
        double gpa = 3.01;
        double temperature = -10.5;

        char grade = 'A';
        char symbol = '@';
        char currency = '$';

        boolean isStudent = false;
        boolean isOnline = false;
        boolean forSale = true;

        // Reference
        String name = "Phumrapee Chaowanapricha";
        String food = "Kra-pow";
        String email = "fake123@gmail.com";
        String car = "Mustang";
        String color = "Black";

        System.out.println("Your choice is a " + color + " " + year + " " + car);
        System.out.println("The price is " + currency + price);
        if(forSale){
            System.out.print("There is a " + car + " for sale.");
        }else{
            System.out.print("The" + car + " is not for sale.");
        }

    }
}
