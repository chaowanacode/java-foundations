// Topic: Constructor chaining with `this(...)`
package w02.oop;

// Constructor chaining = one constructor calling another constructor of the
// SAME class using `this(...)`. It removes duplicated initialisation code:
// one constructor holds the real logic, the others just supply defaults.
public class ConstructorChainingDemo {
    public static void main(String[] args) {
        // ===== Example: No arguments -> all defaults =====
        // Chain: Pizza() -> Pizza(String) -> Pizza(String, String, int)
        Pizza plain = new Pizza();
        plain.printDetails();

        // ===== Example: Some arguments -> the rest defaulted =====
        // Chain: Pizza(String) -> Pizza(String, String, int)
        Pizza cheesy = new Pizza("mozzarella");
        cheesy.printDetails();

        // ===== Example: All arguments -> no chaining needed =====
        // This is the "master" constructor every other one eventually reaches,
        // so the initialisation logic exists in exactly ONE place.
        Pizza deluxe = new Pizza("cheddar", "thin", 14);
        deluxe.printDetails();
    }
}

class Pizza {
    // Defaults kept in one place so the chain has something to pass along.
    static final String DEFAULT_CHEESE = "no cheese";
    static final String DEFAULT_CRUST = "original";
    static final int DEFAULT_SIZE_INCHES = 12;

    String cheese;
    String crust;
    int sizeInches;

    Pizza() {
        // `this(...)` must be the FIRST statement in the constructor.
        // It hands off to the one-argument constructor below.
        this(DEFAULT_CHEESE);
        System.out.println("(finished no-arg constructor)");
    }

    Pizza(String cheese) {
        // ...which in turn hands off to the master constructor.
        this(cheese, DEFAULT_CRUST, DEFAULT_SIZE_INCHES);
        System.out.println("(finished 1-arg constructor)");
    }

    // The master constructor: the only place that actually assigns the fields.
    Pizza(String cheese, String crust, int sizeInches) {
        this.cheese = cheese;
        this.crust = crust;
        this.sizeInches = sizeInches;
        System.out.println("(finished 3-arg constructor - fields assigned here)");
    }

    void printDetails() {
        System.out.println(sizeInches + "\" " + crust + " crust pizza with " + cheese);
    }
}
