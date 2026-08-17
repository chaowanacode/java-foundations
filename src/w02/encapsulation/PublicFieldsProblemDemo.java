// Topic: The problem with public fields (why encapsulation exists)
package w02.encapsulation;

// Encapsulation means keeping an object's data inside the object - like
// medication inside a capsule - so the outside world can only touch it through
// controlled methods. This demo shows what goes wrong WITHOUT that protection.
public class PublicFieldsProblemDemo {
    public static void main(String[] args) {
        // ===== Example: Normal, sensible use =====
        PublicPerson person = new PublicPerson();
        person.name = "Phumrapee";
        person.age = 30;
        System.out.println(person.name + " is " + person.age + " years old.");

        // ===== Example: Nothing stops nonsense data =====
        // The fields are public, so any code anywhere can write straight into
        // them. There is no validation layer to reject impossible values.
        person.age = -5;
        person.name = "";
        System.out.println("'" + person.name + "' is " + person.age + " years old.");

        // ===== Why this is a real problem =====
        // 1. The object is now in an invalid state and it never noticed.
        // 2. The bad value shows up far away from the line that caused it,
        //    which makes it painful to debug.
        // 3. There is no single place to add a rule like "age must be 0..120",
        //    because every caller assigns the field directly.
        System.out.println("Is this person old enough to vote? " + (person.age >= 18));
        System.out.println("Fix: make the fields private and go through setters"
                + " -> see GettersAndSettersDemo.");
    }
}

// The unprotected version: every field is wide open to the outside world.
class PublicPerson {
    public String name;
    public int age;
}
