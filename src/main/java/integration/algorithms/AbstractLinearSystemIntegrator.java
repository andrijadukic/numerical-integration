package integration.algorithms;

import functions.UnivariateVectorFunction;
import integration.util.AbstractLinearSystemIntegrationSubject;
import integration.util.StateStatistics;
import linear.matrix.Matrix;
import linear.vector.Vector;
import util.Named;
import util.Sampling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractLinearSystemIntegrator extends AbstractLinearSystemIntegrationSubject implements LinearSystemIntegrator, Named {

    @Override
    public final List<Vector> solve(Matrix A, Matrix B, UnivariateVectorFunction r, double T, double max, Vector x0) {
        initialize(A, B, T);

        int n = Math.toIntExact(Math.round(max / T)) + 1;
        List<Vector> states = new ArrayList<>(n);

        Vector prev = x0.copy();
        for (double t : Sampling.linspace(0., max, n)) {
            notifyObservers(new StateStatistics(states.size(), t, prev));
            states.add(prev);
            prev = next(prev, r, t);
        }

        return Collections.unmodifiableList(states);
    }

    protected abstract void initialize(Matrix A, Matrix B, double T);

    protected abstract Vector next(Vector xk, UnivariateVectorFunction r, double t);
}
