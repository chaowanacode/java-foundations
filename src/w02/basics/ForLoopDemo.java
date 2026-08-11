// Topic: For Loops
package w02.basics;
import java.util.Scanner;

public class ForLoopDemo {
    public static void main(String[] args) {
        // ===== Example: Basic counting loop =====
        // for loop = execute some code a certain amount of time
        for (int i = 0; i < 10; i++){
            System.out.println("Pizza");
        }

        // ===== Example: User-input loop count =====
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter how many time you want to loop: ");

        int max = scanner.nextInt();
        for (int i = 1; i <= max; i++){
            System.out.println(i);
        }
        scanner.close();

        // ===== Example: Countdown =====
        scanner = new Scanner(System.in);
        System.out.println("How many second to countdown from?: ");
        int start = scanner.nextInt();

        for (int i = start; i > 0; i--){
            System.out.println(i);
        }
        System.out.println("Happy New Year!!!");
    }
}
