/**
 * Proj7.java
 * Christian Bartel 3/18/24 8:00pm
 * 
 * Creates a class for matrices given rows and columns and allows matrix operations to be performed
 * with given method
 * 
 */

public class Matrix {

    //attributes
    private int rows;
    private int cols;
    private int[][] data;

    //constructor
    public Matrix(int rowNum, int colNum) {
        rows = rowNum;
        cols = colNum;
        data = new int[rows][cols];
    }

    //methods

     /**
     * Sets element to value at given row and column
     * @param row Row for given value
     * @param col Column for given value
     * @param value Given value
     */
    public void setElem(int row, int col, int value) {
        data[row][col] = value;
    }
    
    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * adds two matrices
     * @param m Other matrix
     * @return Added matrix
     */
    public Matrix plus(Matrix m) {

        //if dimensions are wrong
        if (rows != m.rows || cols != m.cols) {
            return null;
        }

        //else add
        Matrix addedMatrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                addedMatrix.setElem(i, j, data[i][j] + m.data[i][j]);
            }
        }
        return addedMatrix;
    }

    /**
     * subtracts two matrices
     * @param m Other matrix
     * @return subtracted matrix
     */
    public Matrix minus(Matrix m) {
        //if dimensions are wrong
        if (rows != m.rows || cols != m.cols) {
            return null;
        }

        //else subtract
        Matrix subtractedMatrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                subtractedMatrix.setElem(i, j, data[i][j] - m.data[i][j]);
            }
        }
        return subtractedMatrix;
    }

    /**
     * multiplies two matrices
     * @param m Other matrix
     * @return multiplied matrix
     */
    public Matrix times(Matrix m) {
        //if dimensions are wrong
        if (cols != m.rows) {
            return null;
        }

        //else multiply
        Matrix multipliedMatrix = new Matrix(rows, m.cols);
        int sum;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                sum = 0;
                for (int k = 0; k < cols; k++) {
                    sum += data[i][k] * m.data[k][j];
                }
                multipliedMatrix.data[i][j] = sum;
            }
        }
        return multipliedMatrix;
    }
    
    /**
     * transposes matrix
     * @return transposed matrix
     */
    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposedMatrix.setElem(j, i, data[i][j]);
            }
        }
        return transposedMatrix;
    }

    /**
     * checks if two matrices are equal
     * @param m Other matrix
     * @return Equivalence
     */
    public boolean equals(Matrix m) {
        if (rows != m.rows || cols != m.cols) return false;
        boolean isEqual = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isEqual) {
                    isEqual = data[i][j] == m.data[i][j];
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * makes matrix into string
     * @return Matrix as a string
     */
    public String toString() {
        String string = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                string += data[i][j] + ((j == cols - 1) ? "" : " ");
            }
            string += "\n";
        }
        return string;
    }
    

    private String convertTo16Bit(int num) {
        int first = num / 256;
        int second = num % 256;
        char f = (char) first;
        char s = (char) second;
        return "" + f + s;
    }

    public String to16Bit() {
        String string = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                string += convertTo16Bit(data[i][j]);
            }
        }
        return string;
    }


    public boolean isWithin(Matrix mat, int mod) {
        int center = data[0][0];
        int radius = mod / 4;
        int point = mat.data[0][0];

        for (int i = 0; i < radius + 1; i++) {
            if ((center + i) % mod == point) return true;
            if ((center - i) % mod == point) return true;
        }

        return false;
    }

    public void mod (int m) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                setElem(i, j, data[i][j] % m);
            }
        }
    }

    public Matrix getRowMatrix(int row) {
        Matrix rowMatrix = new Matrix(1, cols);
        for (int i = 0; i < cols; i++) {
            rowMatrix.setElem(0, i, data[row][i]);
        }
        return rowMatrix;
    }

    public Matrix getColMatrix(int col) {
        Matrix colMatrix = new Matrix(rows, 1);
        for (int i = 0; i < rows; i++) {
            colMatrix.setElem(i, 0, data[i][col]);
        }
        return colMatrix;
    }
}
