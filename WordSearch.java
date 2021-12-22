import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


/**
 * LeetCode 79. Word Search
 * https://leetcode.com/problems/word-search/
 */
public class WordSearch {


    // **** global variables (should be converted to arguments) ****
	static int			ROWS    = 0;
	static int			COLS    = 0;
	static boolean[] 	visited = null;


    /**
     * Given an m x n grid of characters board and a string word, 
     * return true if word exists in the grid.
     * 
     * Execution: O(n * m) - Space O(n * m)
     * 
     * Runtime: 82 ms, faster than 92.82% of Java online submissions.
     * Memory Usage: 37.2 MB, less than 61.42% of Java online submissions.
     * 
     * 80 / 80 test cases passed.
     * Status: Accepted
     * Runtime: 89 ms
     * Memory Usage: 37 MB
     */
	static boolean exist0(char[][] board, String word) {
		
		// **** initialization ****
		ROWS    = board.length;
		COLS    = board[0].length;
				
		// **** loop until word is found or all cells have been tried ****
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {

                // **** search for word starting at this cell ****
                if (word.charAt(0) == board[row][col]) {

                    // **** clear visited matrix ****
                    visited = new boolean[ROWS * COLS];

                    // **** depth first search ****
                    if (dfs0(board, row, col, word, 0)) return true;
                }

			}
		}

		// **** word not found ****
		return false;
	}


    /**
     * DFS recursive call.
     * Makes use of visited boolean[][].
     */
	static boolean dfs0(char[][] board, int row, int col, String word, int ndx) {   

        // **** check if we found the word ****
        if (ndx >= word.length()) return true;

        // **** determine if this cell has already been visited ****
        if (visited[(row * COLS) + col] == true) return false;

        // **** get current character from the word (for ease of use) ****
        char c = word.charAt(ndx);

        // **** current word character in this cell ****
        if (c == board[row][col]) {
            
            // **** flag that this cell is being visited ****
            visited[(row * COLS) + col] = true;
            
            // **** determine if the word has been found ****
            if (ndx == (word.length() - 1)) return true;

            // **** search right ****
            if (col < (COLS - 1))
                if (dfs0(board, row, col + 1, word, ndx + 1)) return true;

            // **** search down ****
            if (row < (ROWS - 1))
                if (dfs0(board, row + 1, col, word, ndx + 1)) return true;

            // **** search left ****
            if (col > 0)
                if (dfs0(board, row, col - 1, word, ndx + 1)) return true;
            
            // **** search up ****
            if (row > 0)
                if (dfs0(board, row - 1, col, word, ndx + 1)) return true;

            // **** flag that this cell was not visited ****
            visited[(row * COLS) + col] = false;
        }

        // **** word not found ****
        return false;
    }


    /**
     * Given an m x n grid of characters board and a string word, 
     * return true if word exists in the grid.
     * 
     * Execution: O(n * m) - Space: O(n * m)
     * 
     * Runtime: 89 ms, faster than 88.20% of Java online submissions.
     * Memory Usage: 37 MB, less than 81.90% of Java online submissions.
     * 
     * 80 / 80 test cases passed.
     * Status: Accepted
     * Runtime: 89 ms
     * Memory Usage: 37 MB
     */
    static public boolean exist(char[][] board, String word) {

        // **** loop once per row ****
        for (int r = 0; r < board.length; r++) {

            // **** loop once per column ****
            for (int c = 0; c < board[0].length; c++) {

                // **** search for word (if needed) ****
                // if (word.charAt(0) == board[r][c])
                    if (dfs(board, r, c, word, 0)) return true;
            }
        }

        // **** word not found ****
        return false;
    }


    /**
     * DFS recursive call.
     * Does not make use of visited boolean[][].
     */
    static public boolean dfs(char[][] board, int r, int c,String word, int ndx) {

        // **** base case(s) ****
        if (ndx == word.length())
            return true;
        
            if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c] != word.charAt(ndx))
            return false;

        // **** replace character on board ****
        char tmp    = board[r][c];
        board[r][c] = '*';

        // **** search in all four directions ****
        boolean found = dfs(board, r, c + 1, word, ndx + 1) ||
                        dfs(board, r + 1, c,  word, ndx + 1) ||
                        dfs(board, r, c - 1, word, ndx + 1) ||
                        dfs(board, r - 1, c, word, ndx + 1);

        // **** restore character on board ****
        board[r][c] = tmp;

        // **** word found or not ****
        return found;
    }



 

    /**
     * Generate a string representation of the specified matrix.
     * Auxiliary function.
     * !!! NOT PART OF THE SOLUTION !!!
     */
    static private String displayBoard(char[][] matrix) {

        // **** initialization ****
        StringBuilder sb = new StringBuilder();

        // **** traverse the matrix ****
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[0].length; c++) {
                sb.append(matrix[r][c]);
                if (c < matrix[0].length - 1) sb.append(", ");
                else if (r < matrix.length - 1) sb.append('\n');
            }
        }

        // **** return string with matrix contents ****
        return sb.toString();
    }


    /**
     * Test scaffold
     * !!! NOT PART OF THE SOLUTION !!!
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open a buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read number of rows `rows` ****
        int rows = Integer.parseInt(br.readLine().trim());

        // **** read number of columns `cols` ****
        int cols = Integer.parseInt(br.readLine().trim());

        // **** create `board` ****
        char[][] board = new char[rows][cols];

        // **** read `board` contents ****
        for (int r = 0; r < rows; r++) {

            // **** read next row into Character[] row ****
            Character[] row = Arrays.stream(br.readLine().trim().split(","))
                                .map( x -> x.charAt(1) )
                                .toArray(Character[]::new);

            // **** copy Character[] row  to char[] board[r] ****
            int c = 0;
            for (Object ch : row)
                board[r][c++] = (char)ch;
        }

        // **** read `word` (remove start and end double quotes ****
        String word = br.readLine().trim().replaceAll("^\"|\"$", "");

        // **** close the buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< rows: " + rows);
        System.out.println("main <<< cols: " + cols);
        System.out.println("main <<< board:");
        System.out.println(displayBoard(board));
        System.out.println("main <<< word ==>" + word + "<== len: " + word.length());

        // **** call function of interest and display result ****
        System.out.println("main <<< exist0: " + exist0(board, word));


        // **** call function of interest and display result ****
        System.out.println("main <<<  exist: " + exist(board, word));


    }
}