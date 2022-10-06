package linear.decompose;

import linear.vector.Vectors;
import linear.exceptions.SingularMatrixException;
import linear.matrix.Matrix;
import linear.linalg.LinearAlgebra;
import linear.matrix.Matrices;
import linear.vector.Vector;


/**
 * Implementation of LUP decomposition
 */
public class LUPDecomposer extends AbstractMatrixDecomposer {

    private Matrix L;
    private Matrix U;
    private Vector P;
    private boolean isSwapCountEven = true;
    private final int dimension;

    public LUPDecomposer(Matrix matrix) {
        super(matrix);
        dimension = matrix.getRowDimension();
        decompose();
    }

    @Override
    public boolean isApplicable(Matrix matrix) {
        return Matrices.isSquareMatrix(matrix);
    }

    /**
     * Performs LUP decomposition
     */
    private void decompose() {
        P = Vectors.range(0, dimension);

        for (int i = 0, n = dimension - 1; i < n; i++) {
            int pivot = i;
            for (int j = i + 1, m = n + 1; j < m; j++) {
                if (Math.abs(matrix.get(j, i)) > Math.abs(matrix.get(pivot, i))) {
                    pivot = j;
                }
            }

            if (pivot != i) {
                P.swap(i, pivot);
                matrix.swapRows(i, pivot);
                isSwapCountEven = !isSwapCountEven;
            }

            if (Math.abs(matrix.get(i, i)) < LinearAlgebra.EPSILON) throw new SingularMatrixException();

            for (int j = i + 1, m = n + 1; j < m; j++) {
                matrix.set(j, i, matrix.get(j, i) / matrix.get(i, i));
                for (int k = i + 1; k < m; k++) {
                    matrix.set(j, k, matrix.get(j, k) - matrix.get(j, i) * matrix.get(i, k));
                }
            }
        }
    }

    /**
     * Gets the cached L matrix
     *
     * @return L matrix
     */
    public Matrix getL() {
        if (L != null) return L;

        L = Matrices.empty(dimension);
        for (int i = 0; i < dimension; i++) {
            L.set(i, i, 1);
            for (int j = 0; j < i; j++) {
                L.set(i, j, matrix.get(i, j));
            }
        }
        return L;
    }

    /**
     * Gets the cached U matrix
     *
     * @return U matrix
     */
    public Matrix getU() {
        if (U != null) return U;

        U = Matrices.empty(dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = i; j < dimension; j++) {
                U.set(i, j, matrix.get(i, j));
            }
        }
        return U;
    }

    /**
     * Gets the permutation vector
     *
     * @return permutation vector
     */
    public Vector getPivot() {
        return P;
    }

    @Override
    public double getDeterminant() {
        double det = (isSwapCountEven ? 1. : -1.);
        for (int i = 0; i < dimension; i++) {
            det *= matrix.get(i, i);
        }
        return det;
    }

    @Override
    public LinearEquationSolver solver() {
        return new LUPSolver();
    }

    /**
     * Private class implementing the LinearEquationSolver interface by using LUP decomposition
     */
    private class LUPSolver implements LinearEquationSolver {

        private final Matrix L;
        private final Matrix U;

        private LUPSolver() {
            L = getL();
            U = getU();
        }

        @Override
        public Vector solve(Vector b) {
            return LinearAlgebra.backwardSubstitution(U, LinearAlgebra.forwardSubstitution(L, LinearAlgebra.permute(b, P)));
        }

        @Override
        public Matrix invert() {
            int n = P.getDimension();

            Matrix identity = Matrices.identity(n);
            Vector[] x = new Vector[n];
            for (int i = 0; i < n; i++) {
                x[i] = solve(identity.getColumn(i));
            }

            Matrix result = Matrices.empty(n, n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    result.set(i, j, x[j].get(i));
                }
            }
            return result;
        }
    }
}
