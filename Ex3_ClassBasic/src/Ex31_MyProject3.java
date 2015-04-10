/**
 * Created by Mave on 2015/4/10 0010.
 * Page 116 Question 1: Create class 'Person' and else, print them.
 */
class Person {
    String name;
    int sex;
    int age;

/*    //With no parameters
    Person() {
        this.name = "I am created by Person()";
        this.sex = -1;
        this.age = -1;
    }*/

    //With parameters
    Person(String name, int sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    //Override
    public String toString() {
        return "Name: " + name + "\n" + "Sex: " + sex + "\n" + "Age: " + age;
    }

}

class CreatPerson {
    public static void main(String[] args) {
        Person object1 = new Person("Sharuru", 1, 20);
        System.out.println(object1);
    }
}
