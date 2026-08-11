// Topic: If Statements
package w02.basics;
import java.util.Scanner;

public class IfStatementDemo {
    public static void main(String[] args) {
        // ===== Example: Name / age / student input =====
        //if statement
        Scanner scanner = new Scanner(System.in);

        String name;
        int age;
        boolean isStudent;

        System.out.println("Enter your name: ");
        name = scanner.nextLine();
        System.out.println("Enter your age: ");
        age = scanner.nextInt();
        System.out.println("Are you a student? (true/false): ");
        isStudent = scanner.nextBoolean();

        // Group 1
        if (name.isEmpty()) {
            System.out.println("You didn't enter your name!");
        } else {System.out.println("Hello " + name);}

        // Group 2
        if (age >= 65) {
            System.out.println("You are a senior!");
        } else if(age >= 18){
            System.out.println("You are an adult!");
        } else if (age < 0) {
            System.out.println("You haven't been born yet!");
        } else if (age == 0) {
            System.out.println("You are a baby!");
        }
        else {
            System.out.println("You are a child!");
        }

        // Group 3
        if (isStudent) {
            System.out.println("You are a student!");
        } else {
            System.out.println("You are a NOT student!");
        }
        scanner.close();
    }
}
