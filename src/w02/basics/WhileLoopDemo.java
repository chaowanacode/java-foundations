// Topic: While Loops
package w02.basics;
import java.util.Scanner;

public class WhileLoopDemo {
    public static void main(String[] args) {
        // ===== Example: Name input validation (while) =====
        // while loop
        Scanner scanner = new Scanner(System.in);
        String name = "";

        while (name.isEmpty()) {
            System.out.print("Enter your name: ");
            name = scanner.nextLine();
        }
        System.out.println("Hello " + name);
        scanner.close();

        // ===== Example: Number range validation (do-while) =====
        scanner = new Scanner(System.in);
        int number = 0;

        do {
            System.out.print("Enter a number between 1-10: ");
            number = scanner.nextInt();
        } while (number < 1 || number > 10);
        System.out.println("You picked " + number);
        scanner.close();
    }
}
