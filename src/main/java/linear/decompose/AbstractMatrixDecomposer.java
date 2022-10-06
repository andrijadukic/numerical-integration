package linear.decompose;

import linear.matrix.Matrix;

/**
 * Abstract class implementing the IMatrixDecomposer interface
 */
public abstract class AbstractMatrixDecomposer implements MatrixDecomposer {

    protected final Matrix matrix;

    public AbstractMatrixDecomposer(Matrix matrix) {
        if (!isApplicable(matrix))
            throw new IllegalArgumentException("Given matrix is not applicable for this decomposition");

        this.matrix = matrix.copy();
    }
}
