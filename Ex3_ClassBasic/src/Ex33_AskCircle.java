/**
 * Created by Mave on 2015/4/10 0010.
 * Page 116 Question 3: Create class 'Circle' and else, calculate the perimeter and the area of the giving circle.
 */
class Circle {
    static double r;
    static final double PI = 3.142;

    Circle(double r) {
        Circle.r = r;
    }

    double perimeter() {
        return 2 * PI * r;
    }

    double area() {
        return PI * r * r;
    }
}

class CreatCircle {
    public static void main(String[] args) {
        double r = 2;
        Circle object1 = new Circle(r);
        System.out.println("When r = " + r + " :");
        System.out.println("Circle's perimeter is: " + object1.perimeter());
        System.out.println("Circle's area is: " + object1.area());
    }
}
