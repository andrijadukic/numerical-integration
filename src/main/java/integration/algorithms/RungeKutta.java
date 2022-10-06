package integration.algorithms;

import functions.UnivariateVectorFunction;
import integration.exceptions.IntegratorNotInitializedException;
import linear.matrix.Matrix;
import linear.vector.Vector;

public final class RungeKutta extends AbstractExplicitLinearSystemIntegrator {

    private Matrix A;
    private Matrix B;
    private double T;

    private boolean isInitialized;

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        this.A = A;
        this.B = B;
        this.T = T;

        isInitialized = true;
    }

    @Override
    protected Vector next(Vector xk, UnivariateVectorFunction r, double t) {
        if (!isInitialized) throw new IntegratorNotInitializedException(getClass());

        Vector m1 = f(xk, r.valueAt(t));
        Vector m2 = f(xk.add(m1.multiply(T / 2.)), r.valueAt(t + (T / 2.)));
        Vector m3 = f(xk.add(m2.multiply(T / 2.)), r.valueAt(t + (T / 2.)));
        Vector m4 = f(xk.add(m3.multiply(T)), r.valueAt(t + T));

        return xk.add((m1.add(m2.multiply(2)).add(m3.multiply(2)).add(m4)).multiply(T / 6.));
    }

    private Vector f(Vector x, Vector r) {
        return A.multiply(x).add(B.multiply(r));
    }

    @Override
    public String getName() {
        return "Runge-Kutta";
    }
}
