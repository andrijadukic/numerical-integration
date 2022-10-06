package integration.algorithms;

import functions.UnivariateVectorFunction;
import integration.exceptions.IntegratorNotInitializedException;
import linear.decompose.LUPDecomposer;
import linear.matrix.Matrices;
import linear.matrix.Matrix;
import linear.vector.Vector;

public final class Trapezoidal extends AbstractImplicitLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private double T;

    private Matrix R;
    private Matrix S;

    private boolean isInitialized;

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        this.A = A;
        this.B = B;
        this.T = T;

        Matrix identity = Matrices.identity(A.getRowDimension());
        Matrix halvedA = A.multiply(T / 2.);
        Matrix inverse = new LUPDecomposer(identity.subtract(halvedA)).solver().invert();
        R = inverse.multiply(identity.add(halvedA));
        S = inverse.multiply(T / 2.).multiply(B);

        isInitialized = true;
    }

    @Override
    protected Vector next(Vector xk, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return R.multiply(xk).add(S.multiply(r.valueAt(t).add(r.valueAt(t + T))));
    }

    @Override
    public Vector correct(Vector xk, Vector prediction, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());
        return xk.add(A.multiply(xk).add(B.multiply(r.valueAt(t))).add(A.multiply(prediction)).add(B.multiply(r.valueAt(t + T))).multiply(T / 2.));
    }

    @Override
    public String getName() {
        return "Trapezoidal";
    }
}
