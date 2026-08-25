// Topic: Handling invalid input in a setter - strategy 1: safe default value
package w02.encapsulation;

// When a setter receives a value it does not like, the first option is to
// CORRECT it: clamp the input to the nearest allowed value instead of rejecting
// it. The program keeps running and the object is never left in a bad state.
public class SetterDefaultValueDemo {
    public static void main(String[] args) {
        Employee employee = new Employee("Phumrapee", 30, 50000.0);

        // ===== Example: A valid value passes straight through =====
        employee.setAge(35);
        System.out.println("Valid age 35 -> " + employee.getAge());

        // ===== Example: Too low -> clamped up to the minimum =====
        // The caller asked for -5; the setter decided 0 was the sensible floor.
        employee.setAge(-5);
        System.out.println("Invalid age -5 -> " + employee.getAge() + " (clamped to MIN_AGE)");

        // ===== Example: Too high -> clamped down to the maximum =====
        employee.setAge(250);
        System.out.println("Invalid age 250 -> " + employee.getAge() + " (clamped to MAX_AGE)");

        // ===== Example: The same idea on another field =====
        employee.setSalary(-1000.0);
        System.out.println("Invalid salary -1000 -> " + employee.getSalary() + " (clamped to MIN_SALARY)");

        // ===== Trade-off =====
        // The program never crashes, but the caller silently gets a different
        // value than it asked for. Use this when a sensible fallback exists;
        // use an exception when bad input must not be quietly swallowed
        // -> see SetterExceptionDemo.
        System.out.println("Final state -> " + employee.getName()
                + ", age " + employee.getAge()
                + ", salary " + employee.getSalary());
    }
}

class Employee {
    // The allowed range lives in constants so the rule is stated in one place.
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 120;
    private static final double MIN_SALARY = 0.0;

    private String name;
    private int age;
    private double salary;

    Employee(String name, int age, double salary) {
        // Going through the setters means the constructor gets the same
        // validation as every later assignment - no back door around the rules.
        this.name = name;
        setAge(age);
        setSalary(salary);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getSalary() {
        return salary;
    }

    // Strategy 1: correct the input rather than refuse it.
    public void setAge(int age) {
        if (age < MIN_AGE) {
            System.out.println("  [setAge] " + age + " is below " + MIN_AGE + " - using " + MIN_AGE);
            this.age = MIN_AGE;
        } else if (age > MAX_AGE) {
            System.out.println("  [setAge] " + age + " is above " + MAX_AGE + " - using " + MAX_AGE);
            this.age = MAX_AGE;
        } else {
            this.age = age;
        }
    }

    public void setSalary(double salary) {
        if (salary < MIN_SALARY) {
            System.out.println("  [setSalary] " + salary + " is negative - using " + MIN_SALARY);
            this.salary = MIN_SALARY;
        } else {
            this.salary = salary;
        }
    }
}
