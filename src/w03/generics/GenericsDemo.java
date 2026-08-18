// Topic: Generics
package w03.generics;
import java.util.ArrayList;

public class GenericsDemo {
    public static void main(String[] args) {

        Box<String> box = new Box<>();
        Product<String, Double> product = new Product<>("Apple", 0.55);
        Product<String, Integer> product2 = new Product<>("Ticket", 15);

        box.setItem("Banana");
        System.out.println(box.getItem());

        System.out.println(product.getItem());
        System.out.println(product.getPrice());

        System.out.println(product2.getItem());
        System.out.println(product2.getPrice());

    }
}
