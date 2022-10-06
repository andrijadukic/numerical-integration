package linear.decompose;

import linear.matrix.Matrix;

/**
 * Represents an object used for matrix decomposition
 */
public interface MatrixDecomposer {

    /**
     * Tests whether the given matrix is applicable for this decomposition algorithm
     *
     * @param matrix matrix to be tested
     * @return true if matrix is applicable, false otherwise
     */
    boolean isApplicable(Matrix matrix);

    /**
     * Gets the determinant of the given matrix
     *
     * @return determinant of the given matrix
     */
    double getDeterminant();

    /**
     * Gets a linear equation solver
     *
     * @return solver object
     */
    LinearEquationSolver solver();
}
