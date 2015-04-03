/**
 * Created by Mave on 2015/4/3 0003.
 * Extra question: Print full AZ of 1, 2, 3, 4, 5.
 */
public class Ex24_FullAZ {
    public static void main(String[] args) {
        int[] num = {1, 2, 3, 4, 5};
        perm(num, 0, 4);

    }

    public static void perm(int num[], int head, int tail) {
        if (head == tail) {
            for (int i = 0; i <= tail; i++) {
                System.out.print(num[i] + " ");
            }
            System.out.println();
        } else {
            for (int i = head; i <= tail; i++) {
                swap(num, i, head);
                perm(num, head + 1, tail);
                swap(num, i, head);
            }
        }
    }

    public static void swap(int num[], int a, int b) {
        int temp;
        temp = num[a];
        num[a] = num[b];
        num[b] = temp;
    }
}
