/**
 * Created by Mave on 2015/4/3 0003.
 * Page 89 Question 2: Print graph using two-dimensional array.
 */
public class Ex22_TwoDimensionalArrayPrint {
    public static void main(String[] args) {
        //Initialize array
        boolean graph[][] = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                graph[i][j] = true;
            }
        }
        //Mark special
        int currentLine = 0;
        for (int col = 0; col < 4; col++) {
            for (int row = 3 - col; row >= 0; row--) {
                graph[currentLine][row] = false;
                graph[currentLine][8 - row] = false;
                graph[currentLine + 8 - col * 2][row] = false;
                graph[currentLine + 8 - col * 2][8 - row] = false;
            }
            currentLine++;
        }
        //Print
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(graph[i][j] ? "*" : " ");
            }
            System.out.println();
        }
    }
}
