/**
 * Created by Mave on 2015/4/10 0010.
 * Page 153 Question 1: Create class 'Person' with basic method and then create class 'Student', 'Teacher' to override some method in 'Person'.
 */
abstract class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    Person(String name) {
        this(name, 20);
    }

    public abstract void work();
}

class Student extends Person {
    Student(String name, int age) {
        super(name, age);
    }

    @Override
    public void work() {
        System.out.println("Student in learning...");
    }
}

class Teacher extends Person {
    Teacher(String name) {
        super(name);
    }

    @Override
    public void work() {
        System.out.println("Teacher in teaching...");
    }
}

public class Ex41_AbstractClass {
    public static void main(String[] args) {
        Person object1 = new Teacher("Elichika");
        Person object2 = new Student("Nozomi", 17);

        object1.work();
        object2.work();
    }
}
