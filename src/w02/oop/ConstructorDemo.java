// Topic: Constructors (default, custom, overloading, `this`)
package w02.oop;

// A constructor is a special method that runs automatically when an object is
// created with `new`. Its job is to put the object into a valid starting state.
// Rules: same name as the class, and NO return type (not even void).
public class ConstructorDemo {
    public static void main(String[] args) {
        // ===== Example: The implicit default constructor =====
        // NoConstructor defines no constructor at all, so Java supplies a
        // hidden no-argument one that leaves every field at its default value.
        NoConstructor plain = new NoConstructor();
        System.out.println("Default constructor -> brand: " + plain.brand + ", price: " + plain.price);

        // ===== Example: Custom constructor with arguments =====
        // Values are passed in at the moment of instantiation, so the object is
        // never in a half-built state.
        Car mustang = new Car("Mustang", "Black", 45000.0);
        mustang.printDetails();

        // ===== Example: Constructor overloading =====
        // Several constructors can share the name but differ in parameter list.
        // Java picks the one whose parameters match the arguments you passed.
        Car anonymous = new Car();             // matches the no-arg constructor
        Car civic = new Car("Civic");          // matches the one-arg constructor
        anonymous.printDetails();
        civic.printDetails();

        // NOTE: once you write ANY constructor, Java stops supplying the free
        // default one - that is why Car defines its own no-arg version above.
    }
}

// Helper class: a class with no constructor of its own.
class NoConstructor {
    String brand;   // defaults to null
    double price;   // defaults to 0.0
}

// Helper class: three overloaded constructors.
class Car {
    String brand;
    String color;
    double price;

    // No-argument constructor: hand-written stand-in for the default one.
    Car() {
        brand = "Unknown";
        color = "Unpainted";
        price = 0.0;
    }

    // Overload #2: only the brand is known at creation time.
    Car(String brand) {
        // ===== `this` keyword =====
        // The parameter `brand` shadows (hides) the field `brand` inside this
        // constructor. `this` refers to the object currently being created, so
        // `this.brand` is the field and plain `brand` is the parameter.
        this.brand = brand;
        this.color = "Unpainted";
        this.price = 0.0;
    }

    // Overload #3: everything is known at creation time.
    Car(String brand, String color, double price) {
        this.brand = brand;
        this.color = color;
        this.price = price;
    }

    void printDetails() {
        System.out.println(color + " " + brand + " ($" + price + ")");
    }
}
