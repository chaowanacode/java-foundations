// Topic: Private fields with getters and setters
package w02.encapsulation;

// The standard encapsulation recipe:
//   1. mark every field `private` so outside code cannot touch it directly
//   2. add a public GETTER to read the value safely
//   3. add a public SETTER to write the value through a controlled entry point
// IntelliJ can generate steps 2 and 3 for you (Alt+Insert -> Getter and Setter).
public class GettersAndSettersDemo {
    public static void main(String[] args) {
        Person person = new Person("Phumrapee", 30);

        // ===== Example: Reading through getters =====
        // The caller never sees the fields, only the methods the class chose
        // to expose - the data stays sealed inside the capsule.
        System.out.println(person.getName() + " is " + person.getAge() + " years old.");

        // ===== Example: Writing through setters =====
        person.setName("Kunal");
        person.setAge(31);
        System.out.println(person.getName() + " is " + person.getAge() + " years old.");

        // person.age = -5;     // would not compile: age is private
        // That single compile error is the whole point: every write now has to
        // pass through setAge(), which is where validation can live.

        // ===== Example: A getter can expose a computed value =====
        // Getters are methods, not fields, so a class can publish information
        // it does not actually store. Callers cannot tell the difference.
        System.out.println("Can vote? " + person.isAdult());

        // ===== Example: A field with no setter is read-only =====
        // Leaving the setter out is a deliberate design choice.
        System.out.println("Species (read-only): " + person.getSpecies());
        // person.setSpecies("Cat");    // would not compile: no such method

        // Validation inside the setter is covered in SetterDefaultValueDemo
        // and SetterExceptionDemo.
    }
}

class Person {
    // Step 1: private fields - unreachable from outside this class.
    private String name;
    private int age;
    private final String species = "Human";

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Step 2: getters - controlled read access.
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // No setSpecies(): a getter without a setter makes the property read-only.
    public String getSpecies() {
        return species;
    }

    // Step 3: setters - controlled write access, and the single place where
    // rules about the data can be enforced later on.
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // A "getter" for something the object never stores, only calculates.
    public boolean isAdult() {
        return age >= 18;
    }
}
