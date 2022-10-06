package linear.vector;

import linear.matrix.Matrix;
import util.Copyable;
import util.Matchable;

import java.util.function.DoubleUnaryOperator;

import linear.linalg.LinearAlgebra;
import linear.linalg.Mutability;


/**
 * Interface defining a vector that holds real numbers (double precision)
 */
public interface Vector extends Iterable<Double>, Copyable<Vector>, Matchable {

    /**
     * Creates a new vector of this type
     *
     * @param dimension dimension of new vector
     * @return new vector
     */
    Vector newInstance(int dimension);

    /**
     * Gets the dimension of this vector
     *
     * @return dimension of this vector
     */
    int getDimension();

    /**
     * Gets the element at index i
     *
     * @param i index of the target element
     * @return element at index i
     */
    double get(int i);

    /**
     * Sets the element at index i
     *
     * @param i     index of the target element
     * @param value value to store
     * @return this vector
     */
    Vector set(int i, double value);

    /**
     * Swaps the elements at indices i and j
     *
     * @param i first index
     * @param j second index
     */
    void swap(int i, int j);

    /**
     * Performs vector addition
     *
     * @param other vector to be added to this vector
     * @return new vector
     */
    default Vector add(Vector other) {
        return LinearAlgebra.add(this, other, Mutability.IMMUTABLE);
    }

    /**
     * Performs scalar addition
     *
     * @param value value to be added to this vector
     * @return new vector
     */
    default Vector add(double value) {
        return LinearAlgebra.add(this, value, Mutability.IMMUTABLE);
    }

    /**
     * Performs vector subtraction
     *
     * @param other vector to be subtracted from this vector
     * @return new vector
     */
    default Vector subtract(Vector other) {
        return LinearAlgebra.subtract(this, other, Mutability.IMMUTABLE);
    }

    /**
     * Performs scalar subtraction
     *
     * @param value value to be subtracted from this vector
     * @return new vector
     */
    default Vector subtract(double value) {
        return LinearAlgebra.subtract(this, value, Mutability.IMMUTABLE);
    }

    /**
     * Performs vector-matrix multiplication
     *
     * @param matrix second operand in vector-matrix multiplication
     * @return new vector
     */
    default Vector multiply(Matrix matrix) {
        return LinearAlgebra.multiply(this, matrix);
    }

    /**
     * Performs vector-scalar multiplication
     *
     * @param scalar second operand in matrix-scalar multiplication
     * @return new vector
     */
    default Vector multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, Mutability.IMMUTABLE);
    }

    /**
     * Performs inner vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    default double inner(Vector other) {
        return LinearAlgebra.inner(this, other);
    }

    /**
     * Performs outer vector multiplication
     *
     * @param other second operand in vector-vector multiplication
     * @return new vector
     */
    default Matrix outer(Vector other) {
        return LinearAlgebra.outer(this, other);
    }

    /**
     * Applies function to all elements of vector
     *
     * @param function function to be applied
     * @return new vector
     */
    default Vector apply(DoubleUnaryOperator function) {
        return LinearAlgebra.apply(this, function, Mutability.IMMUTABLE);
    }

    /**
     * Transforms this vector to matrix equivalent
     *
     * @return equivalent matrix
     */
    Matrix asMatrix();

    static Vector of(double... values) {
        return new ArrayVector(values);
    }
}
