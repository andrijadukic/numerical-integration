package integration.algorithms;

import functions.UnivariateVectorFunction;
import linear.matrix.Matrix;
import linear.vector.Vector;

import java.util.List;

public interface LinearSystemIntegrator {

    List<Vector> solve(Matrix A, Matrix B, UnivariateVectorFunction r, double T, double max, Vector x0);
}
