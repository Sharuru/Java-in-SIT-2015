/**
 * Created by Mave on 2015/4/3 0003.
 * Page 89 Question 3: Print graph using unequal array.
 */
public class Ex23_UnequalArrayPrint {
    public static void main(String[] args) {
        String[][] row = new String[6][];
        //Initialize array
        for (int i = 0; i < row.length; i++) {
            String[] col = row[i] = new String[i + 1];
            for (int j = 0; j < col.length; j++) {
                col[j] = "*";
            }
        }
        //Print
        for (int i = 0; i < row.length; i++) {
            for (int j = 0; j < row[i].length; j++) {
                System.out.print(row[i][j]);
            }
            System.out.println();
        }
    }
}
