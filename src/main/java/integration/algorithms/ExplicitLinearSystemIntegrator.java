package integration.algorithms;

import functions.UnivariateVectorFunction;
import linear.vector.Vector;

public interface ExplicitLinearSystemIntegrator extends LinearSystemIntegrator {

    Vector predict(Vector xk, UnivariateVectorFunction r, double t);
}
