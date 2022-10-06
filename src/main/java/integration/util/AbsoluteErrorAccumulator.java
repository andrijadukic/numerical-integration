package integration.util;

import functions.UnivariateVectorFunction;
import linear.vector.Vector;
import linear.vector.Vectors;

public class AbsoluteErrorAccumulator implements LinearSystemIntegrationObserver {

    private final UnivariateVectorFunction function;
    private Vector accumulatedError;

    private boolean isStarted;

    public AbsoluteErrorAccumulator(UnivariateVectorFunction function) {
        this.function = function;
    }

    @Override
    public void update(StateStatistics statistics) {
        Vector approximation = statistics.x();
        Vector actual = function.valueAt(statistics.t());

        if (!isStarted) {
            accumulatedError = Vectors.empty(approximation.getDimension());
            isStarted = true;
        }

        for (int i = 0, n = approximation.getDimension(); i < n; i++) {
            accumulatedError.set(i, accumulatedError.get(i) + Math.abs(approximation.get(i) - actual.get(i)));
        }
    }

    public void clear() {
        for (int i = 0, n = accumulatedError.getDimension(); i < n; i++) {
            accumulatedError.set(i, 0.);
        }
    }

    public Vector getAccumulatedError() {
        return accumulatedError;
    }
}
