// Topic: Methods (parameters, return types, overloading)
package w03.methods;
public class MethodsDemo {
    public static void main(String[] args) {
        int age = 17;
        if (ageCheck(age)){
            System.out.println("You may sign up!!");
        }
        else {
            System.out.println("You must be 18+ to sign up!");
        }
    }
    static void happyBirthday(String name, int age){
        System.out.println("Happy birthday to you!");
        System.out.printf("Happy birthday dear %s!\n", name);
        System.out.printf("You are %d years old!!\n", age);
        System.out.println("Happy birthday to you!\n");
    }

    static double square(int number){
        return number * number;
    }

    static double cube(int number){
        return number * number * number;
    }

    static String getFullName(String first, String last){
        return first + " " + last;
    }

    static boolean ageCheck(int age){
        if(age >= 18){
            return true;
        }
        else {
            return false;
        }
    }

}
