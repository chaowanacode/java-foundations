// Topic: Logical Operators
package w02.basics;

public class LogicalOperatorsDemo {
    public static void main(String[] args) {
        // ===== Example: Weather check (temp + isSunny) =====
        // Logical Operators
        double temp = -20;
        boolean isSunny = true;

        if (temp <= 30 && temp >= 0 && isSunny){
            System.out.println("The weather is good!");
            System.out.println("It's sunny outside!!");
        } else if (temp <= 30 && temp >= 0 && !isSunny) {
            System.out.println("It's CLOUDY outside");
        } else if (temp > 30 || temp < 0) {
            System.out.println("The weather is bad!!!");
        }
    }
}
