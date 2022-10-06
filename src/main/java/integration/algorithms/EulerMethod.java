package integration.algorithms;

import functions.UnivariateVectorFunction;
import integration.exceptions.IntegratorNotInitializedException;
import linear.matrix.Matrices;
import linear.matrix.Matrix;
import linear.vector.Vector;

public final class EulerMethod extends AbstractExplicitLinearSystemIntegrator {

    private Matrix M;
    private Matrix N;

    private boolean isInitialized;

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        M = Matrices.identity(A.getRowDimension()).add(A.multiply(T));
        N = B.multiply(T);

        isInitialized = true;
    }

    @Override
    protected Vector next(Vector xk, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return M.multiply(xk).add(N.multiply(r.valueAt(t)));
    }

    @Override
    public String getName() {
        return "Euler method";
    }
}
