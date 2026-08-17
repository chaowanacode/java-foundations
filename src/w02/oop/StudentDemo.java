// Topic: Object (an instance of a class)
package w02.oop;

// An object is the PHYSICAL manifestation of a class: real memory holding real
// values. It is created with the `new` keyword and reached through a reference
// variable. This demo shows objects being built from the Student blueprint.
public class StudentDemo {
    public static void main(String[] args) {
        // ===== Example: Creating objects with `new` =====
        // `new Student()` builds one object; the reference variable `first`
        // stores the address of that object, not the object itself.
        Student first = new Student();
        Student second = new Student();

        // ===== Example: The dot operator (.) =====
        // The dot operator follows the reference to the object and reads/writes
        // the field (or calls the method) that belongs to THAT object.
        first.name = "Kunal Kushwaha";
        first.rollNumber = 13;
        first.marks = 88.5f;

        second.name = "Phumrapee Chaowanapricha";
        second.rollNumber = 14;
        second.marks = 42.0f;

        // ===== Example: One blueprint, many independent states =====
        // Both objects share the same STRUCTURE (from the class) but each keeps
        // its own DATA - changing one never touches the other.
        first.printDetails();
        second.printDetails();

        System.out.println(first.name + " passed? " + first.hasPassed());
        System.out.println(second.name + " passed? " + second.hasPassed());

        // ===== Example: Fields you never set keep their default values =====
        // Java initialises fields automatically: 0 for int, 0.0 for float,
        // null for reference types. (Local variables get NO such default.)
        Student blank = new Student();
        System.out.println("Defaults -> name: " + blank.name
                + ", rollNumber: " + blank.rollNumber
                + ", marks: " + blank.marks);
    }
}
