// Topic: Overloaded methods (same name, different parameter list)
package w03.methods;
public class OverloadedMethodsDemo {
    public static void main(String[] args) {
        // overloaded method = methods that share the same name, but different parameters.
        String bread = "Flat Bread";
        String cheese = "Mozzarella";
        System.out.println(bakePizza(bread, cheese));
    }
    static double add(double a, double b){
        return a + b;
    }

    static double add(double a, double b, double c){
        return a + b + c;
    }

    static String bakePizza(String bread){
        return bread + " Pizza";
    }

    static String bakePizza(String bread, String cheese){
        return cheese + " " + bread + " Pizza";
    }
}
