/**
 * Created by Mave on 2015/4/3 0003.
 * Page 58 Question 2: set n to 1678, print each digit using division and modulo operation.
 */
public class Ex11_Ask1678 {
    public static void main(String[] args) {
        int n = 1678;
        int a = 1678 % 10;
        int b = 1678 / 10 % 10;
        int c = 1678 / 100 % 10;
        int d = 1678 / 1000 % 10;

        System.out.println("n = " + n);
        System.out.println("Each digit of n is: " + d + ", " + c + ", " + b + ", " + a);
    }
}
