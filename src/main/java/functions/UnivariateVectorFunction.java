package functions;

import linear.vector.Vector;

@FunctionalInterface
public interface UnivariateVectorFunction {

    Vector valueAt(double x);
}
