// Topic: Wrapper classes (primitives as objects)
package w02.oop;

import java.util.ArrayList;

// Every primitive type has a matching wrapper CLASS that represents the same
// value as an object: int -> Integer, char -> Character, double -> Double,
// boolean -> Boolean, and so on. You need them wherever a real object is
// required (collections, generics, null values) instead of a raw primitive.
public class WrapperClassesDemo {
    public static void main(String[] args) {
        // ===== Example: Primitive vs wrapper =====
        int primitive = 7;                  // a plain value
        Integer wrapped = 7;                // an OBJECT holding that value
        System.out.println("primitive = " + primitive + ", wrapped = " + wrapped);

        // ===== Example: Autoboxing and unboxing =====
        // Java converts between the two automatically, so the wrapper feels
        // like a primitive even though an object is being created.
        Integer boxed = primitive;          // autoboxing:  int     -> Integer
        int unboxed = wrapped;              // unboxing:    Integer -> int
        System.out.println("boxed = " + boxed + ", unboxed = " + unboxed);

        Character letter = 'A';             // char    -> Character
        Double price = 99.99;               // double  -> Double
        Boolean forSale = true;             // boolean -> Boolean
        System.out.println(letter + " / " + price + " / " + forSale);

        // ===== Example: The extra methods and fields you get for free =====
        // This is the practical payoff: primitives have no methods at all.
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("Integer.parseInt(\"2026\") + 1 = " + (Integer.parseInt("2026") + 1));
        System.out.println("Integer.toBinaryString(10) = " + Integer.toBinaryString(10));
        System.out.println("Character.isDigit('5') = " + Character.isDigit('5'));
        System.out.println("Character.toUpperCase('a') = " + Character.toUpperCase('a'));
        System.out.println("Double.parseDouble(\"3.01\") = " + Double.parseDouble("3.01"));

        // ===== Example: Why wrappers exist - collections hold objects only =====
        // ArrayList<int> does not compile; ArrayList<Integer> does.
        ArrayList<Integer> marks = new ArrayList<>();
        marks.add(88);                      // autoboxed into an Integer
        marks.add(42);
        int firstMark = marks.get(0);       // unboxed back into an int
        System.out.println("marks = " + marks + ", first as int = " + firstMark);

        // ===== Example: A wrapper can be null, a primitive cannot =====
        Integer missing = null;             // legal - useful for "no value yet"
        // int impossible = null;           // would not compile
        System.out.println("missing = " + missing);

        // ===== Example: The cost of being an object =====
        // Wrappers use more memory than primitives and compare by REFERENCE
        // with ==, which is why 1000 != 1000 below while 100 == 100 (small
        // values come from a shared cache). Always compare with .equals().
        Integer smallA = 100, smallB = 100;
        Integer bigA = 1000, bigB = 1000;
        System.out.println("smallA == smallB -> " + (smallA == smallB));
        System.out.println("bigA == bigB     -> " + (bigA == bigB));
        System.out.println("bigA.equals(bigB) -> " + bigA.equals(bigB));
        // Takeaway: use primitives by default, wrappers only when you truly
        // need object behaviour.
    }
}
