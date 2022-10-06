package linear.matrix;

import util.Copyable;
import util.Matchable;

import java.util.Iterator;
import java.util.function.DoubleUnaryOperator;

import linear.linalg.LinearAlgebra;
import linear.linalg.Mutability;
import linear.vector.Vector;

/**
 * Represents a matrix that holds real numbers (double precision)
 */
public interface Matrix extends Iterable<Vector>, Matchable, Copyable<Matrix> {

    /**
     * Creates a new matrix of this type
     *
     * @param rows    row dimension of new matrix
     * @param columns column dimension of new matrix
     * @return new matrix of this type
     */
    Matrix newInstance(int rows, int columns);

    /**
     * Gets row dimension
     *
     * @return row dimension
     */
    int getRowDimension();

    /**
     * Gets row dimension
     *
     * @return row dimension
     */
    int getColumnDimension();

    /**
     * Gets the element at index [i, j]
     *
     * @param i row index
     * @param j column index
     * @return element at index [i, j]
     */
    double get(int i, int j);

    /**
     * Gets the row at index
     *
     * @param index row index
     * @return row at index
     */
    Vector getRow(int index);

    /**
     * Gets the column at index
     *
     * @param index column index
     * @return column at index
     */
    Vector getColumn(int index);

    /**
     * Sets the element at index [i, j]
     *
     * @param i     row index
     * @param j     column index
     * @param value value to store
     * @return this matrix
     */
    Matrix set(int i, int j, double value);

    Iterator<Double> elementIterator();

    /**
     * Swaps rows at index i and j
     *
     * @param i first row
     * @param j second row
     */
    void swapRows(int i, int j);

    /**
     * Swaps columns at index i and j
     *
     * @param i first column
     * @param j second column
     */
    void swapColumns(int i, int j);

    /**
     * Performs matrix addition
     *
     * @param other matrix to be added to this matrix
     * @return new matrix
     */
    default Matrix add(Matrix other) {
        return LinearAlgebra.add(this, other, Mutability.IMMUTABLE);
    }

    /**
     * Performs scalar addition
     *
     * @param value value to be added to this matrix
     * @return new matrix
     */
    default Matrix add(double value) {
        return LinearAlgebra.add(this, value, Mutability.IMMUTABLE);
    }

    /**
     * Performs matrix subtraction
     *
     * @param other matrix to be subtracted from this matrix
     * @return new matrix
     */
    default Matrix subtract(Matrix other) {
        return LinearAlgebra.subtract(this, other, Mutability.IMMUTABLE);
    }

    /**
     * Performs scalar subtraction
     *
     * @param value value to be subtracted from this matrix
     * @return new matrix
     */
    default Matrix subtract(double value) {
        return LinearAlgebra.subtract(this, value, Mutability.IMMUTABLE);
    }

    /**
     * Performs matrix-matrix multiplication
     *
     * @param other second operand in matrix multiplication
     * @return new matrix
     */
    default Matrix multiply(Matrix other) {
        return LinearAlgebra.multiply(this, other);
    }

    /**
     * Performs matrix-vector multiplication
     *
     * @param vector
     * @return new vector
     */
    default Vector multiply(Vector vector) {
        return LinearAlgebra.multiply(this, vector);
    }

    /**
     * Performs matrix-scalar multiplication
     *
     * @param scalar second operand in matrix-scalar multiplication
     * @return new matrix
     */
    default Matrix multiply(double scalar) {
        return LinearAlgebra.multiply(this, scalar, Mutability.IMMUTABLE);
    }

    /**
     * Transposes this matrix
     *
     * @return transposed matrix
     */
    Matrix transpose();

    /**
     * Applies function to all elements of matrix
     *
     * @param function function to be applied
     * @return new matrix
     */
    default Matrix apply(DoubleUnaryOperator function) {
        return LinearAlgebra.apply(this, function, Mutability.IMMUTABLE);
    }

    /**
     * Turns this matrix into vector array by columns
     *
     * @return column vector array
     */
    Vector[] columns();

    /**
     * Turns this matrix into vector array by rows
     *
     * @return row vector array
     */
    Vector[] rows();

    /**
     * Gets this matrix in array form
     *
     * @return array representation of this matrix
     */
    double[][] asArray();
}
