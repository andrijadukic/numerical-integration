package integration.algorithms;

import functions.UnivariateVectorFunction;
import linear.matrix.Matrix;
import linear.vector.Vector;

import java.security.InvalidParameterException;

public final class PECE extends AbstractLinearSystemIntegrator {

    private static final int DEFAULT_CORRECTOR_APPLICATION_COUNT = 1;

    private final AbstractExplicitLinearSystemIntegrator predictor;
    private final AbstractImplicitLinearSystemIntegrator corrector;
    private final int n;

    public PECE(AbstractExplicitLinearSystemIntegrator predictor, AbstractImplicitLinearSystemIntegrator corrector, int n) {
        if (n < 1) throw new InvalidParameterException();

        this.predictor = predictor;
        this.corrector = corrector;
        this.n = n;
    }

    public PECE(AbstractExplicitLinearSystemIntegrator predictor, AbstractImplicitLinearSystemIntegrator corrector) {
        this(predictor, corrector, DEFAULT_CORRECTOR_APPLICATION_COUNT);
    }

    @Override
    protected void initialize(Matrix A, Matrix B, double T) {
        predictor.initialize(A, B, T);
        corrector.initialize(A, B, T);
    }

    @Override
    protected Vector next(Vector xk, UnivariateVectorFunction r, double t) {
        Vector prediction = predictor.predict(xk, r, t);
        int leftover = n;
        while (leftover != 0) {
            prediction = corrector.correct(xk, prediction, r, t);
            leftover--;
        }
        return prediction;
    }

    @Override
    public String getName() {
        return n == 1 ? "PECE" : "PE(CE)^" + n;
    }
}
