// Topic: Handling invalid input in a setter - strategy 2: throw an exception
package w02.encapsulation;

// The second option when a setter receives an unacceptable value: refuse it
// loudly by throwing a RuntimeException. Execution of that call stops
// immediately, so the object is never assigned the bad value and the caller is
// told about the mistake instead of quietly getting something else.
public class SetterExceptionDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Phumrapee", 500.0);

        // ===== Example: A valid value is accepted =====
        account.setBalance(750.0);
        System.out.println("Valid balance -> " + account.getBalance());

        // ===== Example: An invalid value is rejected =====
        // try/catch is used here so the demo can keep running and print the
        // message; without it the exception would end the program.
        try {
            account.setBalance(-100.0);
            System.out.println("This line never runs.");
        } catch (RuntimeException e) {
            System.out.println("Rejected: " + e.getMessage());
        }

        // The assignment never happened - the object still holds the last
        // value it accepted, which is exactly the guarantee we wanted.
        System.out.println("Balance is still -> " + account.getBalance());

        // ===== Example: Rejecting a null / empty String =====
        try {
            account.setOwner("   ");
        } catch (RuntimeException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        System.out.println("Owner is still -> " + account.getOwner());

        // ===== Example: What an uncaught exception looks like =====
        // Uncomment the line below to see the program stop with a stack trace
        // instead of continuing - the "stop execution" behaviour in full.
        // account.setBalance(-1.0);

        // ===== Trade-off =====
        // Nothing invalid ever slips through, but the caller must be ready to
        // handle the failure. Prefer this when a wrong value is a genuine bug;
        // prefer a clamped default when a sensible fallback exists
        // -> see SetterDefaultValueDemo.
        System.out.println("Done - the account never held an invalid value.");
    }
}

class BankAccount {
    private String owner;
    private double balance;

    BankAccount(String owner, double balance) {
        setOwner(owner);
        setBalance(balance);
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    // Strategy 2: validate, and throw if the value breaks the rule.
    public void setBalance(double balance) {
        if (balance < 0) {
            // Throwing exits the method right here, so the field below is
            // never touched by an invalid value.
            throw new RuntimeException("Balance cannot be negative (got " + balance + ")");
        }
        this.balance = balance;
    }

    public void setOwner(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new RuntimeException("Owner name cannot be empty");
        }
        this.owner = owner;
    }
}
