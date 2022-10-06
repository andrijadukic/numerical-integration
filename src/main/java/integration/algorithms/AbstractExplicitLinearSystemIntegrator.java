package integration.algorithms;

import functions.UnivariateVectorFunction;
import linear.vector.Vector;

public abstract class AbstractExplicitLinearSystemIntegrator extends AbstractLinearSystemIntegrator implements ExplicitLinearSystemIntegrator {

    @Override
    public Vector predict(Vector xk, UnivariateVectorFunction r, double t) {
        return next(xk, r, t);
    }
}
