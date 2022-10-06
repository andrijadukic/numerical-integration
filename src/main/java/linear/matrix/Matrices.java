package linear.matrix;

import linear.matrix.Matrix;
import linear.matrix.ArrayMatrix;
import linear.linalg.LinearAlgebra;
import linear.linalg.Mutability;

import java.util.function.DoubleSupplier;

/**
 * Utility class with factory methods for building matrices and checking properties
 */
public class Matrices {

    /**
     * Builds a new empty square matrix
     *
     * @param dimension dimension of new matrix
     * @return new empty matrix
     */
    public static Matrix empty(int dimension) {
        return empty(dimension, dimension);
    }

    /**
     * Builds a new empty matrix
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @return new empty matrix
     */
    public static Matrix empty(int rows, int columns) {
        return new ArrayMatrix(rows, columns);
    }

    /**
     * Builds a new identity matrix
     *
     * @param dimension dimension of new matrix
     * @return new identity matrix
     */
    public static Matrix identity(int dimension) {
        return diagonal(dimension, 1.);
    }

    /**
     * Builds a new diagonal matrix
     *
     * @param dimension dimension of new matrix
     * @return new identity matrix
     */
    public static Matrix diagonal(int dimension, double value) {
        Matrix matrix = empty(dimension);
        for (int i = 0; i < dimension; i++) {
            matrix.set(i, i, value);
        }
        return matrix;
    }

    /**
     * Fills matrix with given value
     *
     * @param matrix matrix to be filled
     * @param value  value
     * @return filled matrix
     */
    public static Matrix fill(Matrix matrix, double value) {
        return LinearAlgebra.apply(matrix, x -> value, Mutability.MUTABLE);
    }

    /**
     * Fills matrix using given supplier
     *
     * @param matrix   matrix to be filled
     * @param supplier supplier
     * @return filled matrix
     */
    public static Matrix fill(Matrix matrix, DoubleSupplier supplier) {
        return LinearAlgebra.apply(matrix, x -> supplier.getAsDouble(), Mutability.MUTABLE);
    }

    /**
     * Tests if given matrix is square
     *
     * @param matrix matrix to be tested
     * @return true if matrix is square, else otherwise
     */
    public static boolean isSquareMatrix(Matrix matrix) {
        return matrix.getRowDimension() == matrix.getColumnDimension();
    }

    /**
     * Tests if given matrix is lower triangle matrix
     *
     * @param matrix matrix to be tested
     * @return true if matrix is lower triangle matrix, else otherwise
     */
    public static boolean isLowerTriangleMatrix(Matrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix.get(i, j) > LinearAlgebra.EPSILON) return false;
            }
        }
        return true;
    }

    /**
     * Tests if given matrix is upper triangle matrix
     *
     * @param matrix matrix to be tested
     * @return true if matrix is upper triangle matrix, else otherwise
     */
    public static boolean isUpperTriangleMatrix(Matrix matrix) {
        if (!isSquareMatrix(matrix)) return false;

        for (int i = 0, n = matrix.getRowDimension(); i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix.get(i, j) > LinearAlgebra.EPSILON) return false;
            }
        }
        return true;
    }
}
