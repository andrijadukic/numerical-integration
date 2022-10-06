package integration.algorithms;

import functions.UnivariateVectorFunction;
import linear.vector.Vector;

public interface ImplicitLinearSystemIntegrator extends LinearSystemIntegrator {

    Vector correct(Vector xk, Vector prediction, UnivariateVectorFunction r, double t);
}
