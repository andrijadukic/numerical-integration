package linear.linalg;

import linear.exceptions.DimensionMismatchException;
import linear.exceptions.MatrixDimensionMismatchException;
import linear.matrix.Matrix;
import linear.matrix.Matrices;
import linear.vector.Vector;

final class LinearAlgebraUtil {

    /**
     * Checks if dimensions of given matrices are the same
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    static void checkDimensionsSame(Matrix m1, Matrix m2) {
        int r1 = m1.getRowDimension();
        int c1 = m1.getColumnDimension();
        int r2 = m2.getRowDimension();
        int c2 = m2.getColumnDimension();

        if (!(r1 == r2 && c1 == c2)) throw new MatrixDimensionMismatchException(r1, c1, r2, c2);
    }

    /**
     * Checks if dimensions of given vertices are the same
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    static void checkDimensionsSame(Vector v1, Vector v2) {
        int d1 = v1.getDimension();
        int d2 = v2.getDimension();

        if (d1 != d2) throw new DimensionMismatchException(d1, d2);
    }

    /**
     * Checks if matrix-matrix addition of given matrices is applicable
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    static void checkAdditionApplicable(Matrix m1, Matrix m2) {
        checkDimensionsSame(m1, m2);
    }

    /**
     * Checks if vector-vector addition of given matrices is applicable
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    static void checkAdditionApplicable(Vector v1, Vector v2) {
        checkDimensionsSame(v1, v2);
    }

    /**
     * Checks if matrix-matrix multiplication of given matrices is applicable
     *
     * @param m1 first matrix
     * @param m2 second matrix
     */
    static void checkMultiplicationApplicable(Matrix m1, Matrix m2) {
        int columnDimension = m1.getColumnDimension();
        int rowDimension = m2.getRowDimension();

        if (columnDimension != rowDimension) throw new DimensionMismatchException(columnDimension, rowDimension);
    }

    /**
     * Checks if matrix-vector multiplication is applicable
     *
     * @param matrix matrix operand
     * @param vector vector operand
     */
    static void checkMultiplicationApplicable(Matrix matrix, Vector vector) {
        int columnDimension = matrix.getColumnDimension();
        int vectorDimension = vector.getDimension();

        if (columnDimension != vectorDimension) throw new DimensionMismatchException(columnDimension, vectorDimension);
    }

    /**
     * Checks if vector-matrix multiplication is applicable
     *
     * @param matrix matrix operand
     * @param vector vector operand
     */
    static void checkMultiplicationApplicable(Vector vector, Matrix matrix) {
        int rowDimension = matrix.getRowDimension();
        int vectorDimension = vector.getDimension();

        if (rowDimension != vectorDimension) throw new DimensionMismatchException(rowDimension, vectorDimension);
    }

    /**
     * Checks if vector-vector multiplication is applicable
     *
     * @param v1 first vector
     * @param v2 second vector
     */
    static void checkMultiplicationApplicable(Vector v1, Vector v2) {
        checkDimensionsSame(v1, v2);
    }

    /**
     * Tests if forward substitution is applicable with these parameters
     * (vector b is of the same dimensions as the row iteration of matrix L and matrix L is lower triangle matrix)
     *
     * @param matrix L matrix
     * @param vector b vector
     * @return true if forward substitution is applicable, false otherwise
     */
    static boolean isForwardSubstitutionApplicable(Matrix matrix, Vector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isLowerTriangleMatrix(matrix);
    }

    /**
     * Tests if backward substitution is applicable with these parameters
     * (vector y is of the same dimensions as the row iteration of matrix U and matrix U is upper triangle matrix)
     *
     * @param matrix U matrix
     * @param vector y vector
     * @return true if backward substitution is applicable, false otherwise
     */
    static boolean isBackwardSubstitutionApplicable(Matrix matrix, Vector vector) {
        return vector.getDimension() == matrix.getRowDimension() && Matrices.isUpperTriangleMatrix(matrix);
    }
}
