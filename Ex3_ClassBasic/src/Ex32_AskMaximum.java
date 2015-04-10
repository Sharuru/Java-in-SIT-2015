/**
 * Created by Mave on 2015/4/10 0010.
 * Page 116 Question 2: Create class 'MaxArray' and ask the maximum number of the array.
 */
public class Ex32_AskMaximum {
    public static void main(String[] args) {
        int test[] = {1, 3, 7, 5, 9, 2, 6, 8, 10, 4, 0};
        MaxArray num = new MaxArray(test);
        System.out.println("The maximum number is: " + num.Maximum());
    }
}

class MaxArray {
    int num[];

    MaxArray(int num[]) {
        this.num = num;
    }

    int Maximum() {
        int max = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] > max) {
                max = num[i];
            }
        }
        return max;
    }
}
