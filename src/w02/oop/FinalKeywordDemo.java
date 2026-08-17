// Topic: The `final` keyword
package w02.oop;

// `final` means "this cannot be changed after it is set". It applies to
// variables (no reassignment), methods (no overriding) and classes (no
// inheriting). Convention: final constants are named in UPPER_SNAKE_CASE.
public class FinalKeywordDemo {

    // ===== final constant =====
    // static + final = one shared, unchangeable value for the whole program.
    static final double PI = 3.14159;

    public static void main(String[] args) {
        // ===== Example: final local variable =====
        // Best initialised at the point of declaration, as here.
        final int MAX_ATTEMPTS = 3;
        System.out.println("MAX_ATTEMPTS = " + MAX_ATTEMPTS + ", PI = " + PI);
        // MAX_ATTEMPTS = 5;   // would not compile: cannot assign a final variable

        // ===== Example: final field set by the constructor =====
        // A final field must be assigned exactly once, either at declaration or
        // inside every constructor - after that the object's id is locked in.
        Account account = new Account("AC-001", 500.0);
        System.out.println("id = " + account.id + ", balance = " + account.balance);
        account.deposit(250.0);             // the non-final field can still change
        System.out.println("after deposit -> balance = " + account.balance);
        // account.id = "AC-002";           // would not compile: id is final

        // ===== Example: final REFERENCE vs the object's state =====
        // This is the part that trips people up: final locks the reference, not
        // the object it points at. The pointer can never move, but the object's
        // internals are still fully mutable.
        final StringBuilder note = new StringBuilder("Hello");
        note.append(" world");              // allowed: mutating the object
        System.out.println("note = " + note);
        // note = new StringBuilder("Bye"); // would not compile: reassigning the reference

        // ===== Example: final method and final class =====
        // Both exist to protect behaviour that must not be swapped out.
        new Dog().describe();
        System.out.println("Config port = " + new Config().port);
    }
}

class Account {
    final String id;    // blank final: no value yet, must be set in the constructor
    double balance;     // not final, so it can change over the account's lifetime

    Account(String id, double balance) {
        this.id = id;               // the one and only assignment allowed
        this.balance = balance;
    }

    void deposit(double amount) {
        balance += amount;
    }
}

class Animal {
    // A final METHOD cannot be overridden by a subclass - the behaviour is
    // guaranteed to stay exactly as written here.
    final void breathe() {
        System.out.println("Breathing (this method can never be overridden).");
    }

    void describe() {
        System.out.println("Some animal.");
    }
}

class Dog extends Animal {
    // void breathe() { }   // would not compile: cannot override a final method

    @Override
    void describe() {       // fine: describe() is not final
        System.out.println("A dog.");
        breathe();
    }
}

// A final CLASS cannot be extended at all, which freezes its whole design.
// java.lang.String is the most famous example of this.
final class Config {
    int port = 8080;
}

// class BetterConfig extends Config { }   // would not compile: Config is final
