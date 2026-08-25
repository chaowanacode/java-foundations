package w03.arraylist;

public class Varargs {
    public static void main(String[] args) {
        System.out.println(add(1, 2, 3, 4));
    }

    static int add(int... numbers){
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
