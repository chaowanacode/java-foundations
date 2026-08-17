// Topic: Variable scope (local, instance, class/static)
package w03.methods;
public class VariableScopeDemo {

    static int x = 1; // CLASS

    public static void main(String[] args) {
        int x = 2; // LOCAL
        System.out.println(x);
        doSomething();
    }

    static void doSomething(){
        int x = 3; // LOCAL
        System.out.println(x);
    }
}
