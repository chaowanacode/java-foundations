// Topic: Class (the blueprint)
package w02.oop;

// A class is a LOGICAL blueprint / template - a user-defined data type that
// bundles related data (fields) and behaviour (methods) into one entity.
// The class itself occupies no memory at runtime; it only describes WHAT every
// object made from it will look like. Objects made from it hold the real data.
// Convention: class names start with a capital letter.
// Run StudentDemo to see objects actually built from this blueprint.
public class Student {

    // ===== Fields (instance variables) =====
    // Each object created from this class gets its OWN copy of these fields,
    // so two Student objects can hold completely different state.
    // Note how the class groups different data types into a single entity.
    String name;      // reference type -> the object holds a pointer to a String
    int rollNumber;   // primitive -> the value lives inside the object on the heap
    float marks;      // primitive

    // ===== Behaviour (methods) =====
    // Methods define what an object can DO. They operate on the fields of
    // whichever object they were called on (the "current" object).
    void printDetails() {
        System.out.println("Roll " + rollNumber + " | " + name + " | marks: " + marks);
    }

    boolean hasPassed() {
        return marks >= 50.0f;
    }
}
