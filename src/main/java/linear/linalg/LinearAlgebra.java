package linear.linalg;

import linear.exceptions.DimensionMismatchException;
import linear.exceptions.SingularMatrixException;
import linear.matrix.Matrix;
import linear.matrix.Matrices;

import java.util.Iterator;
import java.util.function.DoubleBinaryOperator;

import java.util.function.DoubleUnaryOperator;

import linear.vector.Vector;

import java.security.InvalidParameterException;
import java.util.function.DoublePredicate;

import static linear.linalg.LinearAlgebraUtil.*;

/**
 * Class implementing common linear algebra operations as static methods
 */
public class LinearAlgebra {

    public static final double EPSILON = 1e-6;

    /**
     * Performs matrix-matrix addition
     *
     * @param m1         first matrix
     * @param m2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static Matrix add(Matrix m1, Matrix m2, Mutability mutability) {
        return apply(m1, m2, Double::sum, mutability);
    }

    /**
     * Performs matrix-scalar addition
     *
     * @param matrix     matrix
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static Matrix add(Matrix matrix, double value, Mutability mutability) {
        if (value == 0) return (mutability == Mutability.MUTABLE) ? matrix : matrix.copy();
        return apply(matrix, x -> x + value, mutability);
    }

    /**
     * Performs vector-vector addition
     *
     * @param v1         first matrix
     * @param v2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static Vector add(Vector v1, Vector v2, Mutability mutability) {
        return apply(v1, v2, Double::sum, mutability);
    }

    /**
     * Performs vector-scalar addition
     *
     * @param vector     matrix
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static Vector add(Vector vector, double value, Mutability mutability) {
        if (value == 0) return (mutability == Mutability.MUTABLE) ? vector : vector.copy();
        return apply(vector, x -> x + value, mutability);
    }

    /**
     * Performs matrix-matrix subtraction
     *
     * @param m1         first matrix
     * @param m2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static Matrix subtract(Matrix m1, Matrix m2, Mutability mutability) {
        return apply(m1, m2, (x, y) -> x - y, mutability);
    }

    /**
     * Performs matrix-scalar subtraction
     *
     * @param matrix     matrix
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static Matrix subtract(Matrix matrix, double value, Mutability mutability) {
        if (value == 0) return (mutability == Mutability.MUTABLE) ? matrix : matrix.copy();
        return apply(matrix, x -> x - value, mutability);
    }

    /**
     * Performs vector-vector subtraction
     *
     * @param v1         first matrix
     * @param v2         second matrix
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static Vector subtract(Vector v1, Vector v2, Mutability mutability) {
        return apply(v1, v2, (x, y) -> x - y, mutability);
    }

    /**
     * Performs vector-scalar subtraction
     *
     * @param vector     vector
     * @param value      scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static Vector subtract(Vector vector, double value, Mutability mutability) {
        if (value == 0) return (mutability == Mutability.MUTABLE) ? vector : vector.copy();
        return apply(vector, x -> x - value, mutability);
    }

    /**
     * Performs matrix-matrix multiplication
     *
     * @param m1 first matrix
     * @param m2 second matrix
     * @return result matrix
     */
    public static Matrix multiply(Matrix m1, Matrix m2) {
        LinearAlgebraUtil.checkMultiplicationApplicable(m1, m2);

        int r1 = m1.getRowDimension();
        int c1 = m1.getColumnDimension();
        int c2 = m2.getColumnDimension();
        Matrix result = m1.newInstance(r1, c2);

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                double sum = 0.;
                for (int k = 0; k < c1; k++) {
                    sum += m1.get(i, k) * m2.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        return result;
    }

    /**
     * Performs matrix-vector multiplication
     *
     * @param vector vector
     * @param matrix matrix
     * @return result vector
     */
    public static Vector multiply(Matrix matrix, Vector vector) {
        LinearAlgebraUtil.checkMultiplicationApplicable(matrix, vector);

        int n = matrix.getRowDimension();
        Vector result = vector.newInstance(n);
        for (int i = 0; i < n; i++) {
            double sum = 0.;
            for (int j = 0, m = matrix.getColumnDimension(); j < m; j++) {
                sum += matrix.get(i, j) * vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    /**
     * Performs vector-matrix multiplication
     *
     * @param vector vector
     * @param matrix matrix
     * @return result vector
     */
    public static Vector multiply(Vector vector, Matrix matrix) {
        LinearAlgebraUtil.checkMultiplicationApplicable(vector, matrix);

        int n = matrix.getColumnDimension();
        Vector result = vector.newInstance(n);
        for (int i = 0; i < n; i++) {
            double sum = 0.;
            for (int j = 0, m = matrix.getRowDimension(); j < m; j++) {
                sum += matrix.get(j, i) * vector.get(j);
            }
            result.set(i, sum);
        }
        return result;
    }

    /**
     * Performs matrix-scalar multiplication
     *
     * @param matrix     matrix
     * @param scalar     scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static Matrix multiply(Matrix matrix, double scalar, Mutability mutability) {
        if (scalar == 1) return (mutability == Mutability.MUTABLE) ? matrix : matrix.copy();
        return apply(matrix, x -> x * scalar, mutability);
    }

    /**
     * Performs inner vector multiplication
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return result scalar value
     */
    public static double inner(Vector v1, Vector v2) {
        LinearAlgebraUtil.checkMultiplicationApplicable(v1, v2);

        double sum = 0.;
        for (int i = 0, n = v1.getDimension(); i < n; i++) {
            sum += v1.get(i) * v2.get(i);
        }
        return sum;
    }


    /**
     * Performs outer multiplication
     *
     * @param v1 first vector
     * @param v2 second vector
     * @return result matrix
     */
    public static Matrix outer(Vector v1, Vector v2) {
        int rowDimension = v1.getDimension();
        int columnDimension = v2.getDimension();
        Matrix result = Matrices.empty(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            double xi = v1.get(i);
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, xi * v2.get(j));
            }
        }
        return result;
    }

    /**
     * Performs vector-scalar multiplication
     *
     * @param vector     matrix
     * @param scalar     scalar value
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result vector
     */
    public static Vector multiply(Vector vector, double scalar, Mutability mutability) {
        if (scalar == 1) return mutability == Mutability.MUTABLE ? vector : vector.copy();
        return apply(vector, x -> x * scalar, mutability);
    }

    /**
     * Applies operator to all elements of given matrix
     *
     * @param matrix     matrix
     * @param operator   operator to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return result matrix
     */
    public static Matrix apply(Matrix matrix, DoubleUnaryOperator operator, Mutability mutability) {
        int rowDimension = matrix.getRowDimension();
        int columnDimension = matrix.getColumnDimension();
        Matrix result = (mutability == Mutability.MUTABLE) ? matrix : matrix.newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, operator.applyAsDouble(matrix.get(i, j)));
            }
        }
        return result;
    }

    /**
     * Applies operator to all elements of given matrices
     *
     * @param m1         first matrix
     * @param m2         second matrix
     * @param operator   operator to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     */
    public static Matrix apply(Matrix m1, Matrix m2, DoubleBinaryOperator operator, Mutability mutability) {
        LinearAlgebraUtil.checkDimensionsSame(m1, m2);

        int rowDimension = m1.getRowDimension();
        int columnDimension = m1.getColumnDimension();
        Matrix result = (mutability == Mutability.MUTABLE) ? m1 : m1.newInstance(rowDimension, columnDimension);
        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                result.set(i, j, operator.applyAsDouble(m1.get(i, j), m2.get(i, j)));
            }
        }
        return result;
    }

    /**
     * Applies operator to all elements of given vector
     *
     * @param vector     vector
     * @param operator   operator to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return new vector
     */
    public static Vector apply(Vector vector, DoubleUnaryOperator operator, Mutability mutability) {
        int n = vector.getDimension();
        Vector result = (mutability == Mutability.MUTABLE) ? vector : vector.newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, operator.applyAsDouble(vector.get(i)));
        }
        return result;
    }

    /**
     * Applies operator to all elements of given vectors
     *
     * @param v1         first vector
     * @param v2         second vector
     * @param operator   operator to be applied
     * @param mutability if set to MUTABLE then result overwrites first operand
     * @return new vector
     */
    public static Vector apply(Vector v1, Vector v2, DoubleBinaryOperator operator, Mutability mutability) {
        LinearAlgebraUtil.checkDimensionsSame(v1, v2);

        int n = v1.getDimension();
        Vector result = (mutability == Mutability.MUTABLE) ? v1 : v1.newInstance(n);
        for (int i = 0; i < n; i++) {
            result.set(i, operator.applyAsDouble(v1.get(i), v2.get(i)));
        }
        return result;
    }

    /**
     * Tests if any element matches predicate
     *
     * @param iterator  iterator object
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    public static boolean anyMatch(Iterator<Double> iterator, DoublePredicate predicate) {
        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())) return true;
        }
        return false;
    }

    /**
     * Tests if all elements match predicate
     *
     * @param iterator  iterator object
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    public static boolean allMatch(Iterator<Double> iterator, DoublePredicate predicate) {
        return !anyMatch(iterator, x -> !predicate.test(x));
    }

    /**
     * Calculates euclidean norm of a vector
     *
     * @param vector vector
     * @return euclidean norm of a vector
     */
    public static double norm(Vector vector) {
        return Math.sqrt(LinearAlgebra.inner(vector, vector));
    }

    /**
     * Performs forward substitution (Ly = b)
     *
     * @param matrix L matrix
     * @param vector b vector
     * @return result vector y
     */
    public static Vector forwardSubstitution(Matrix matrix, Vector vector) {
        if (!isForwardSubstitutionApplicable(matrix, vector))
            throw new InvalidParameterException("Forward substitution is not applicable with these parameters");

        Vector result = vector.copy();
        for (int i = 0, n = matrix.getRowDimension() - 1; i < n; i++) {
            for (int j = i + 1, m = n + 1; j < m; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
    }

    /**
     * Performs backward substitution (Ux = y)
     *
     * @param matrix U matrix
     * @param vector y vector
     * @return result vector x
     */
    public static Vector backwardSubstitution(Matrix matrix, Vector vector) {
        if (!isBackwardSubstitutionApplicable(matrix, vector))
            throw new InvalidParameterException("Backward substitution is not applicable with these parameters");

        Vector result = vector.copy();
        for (int i = matrix.getRowDimension() - 1; i >= 0; i--) {
            if (Math.abs(matrix.get(i, i)) < EPSILON) throw new SingularMatrixException();

            result.set(i, result.get(i) / matrix.get(i, i));
            for (int j = 0; j < i; j++) {
                result.set(j, result.get(j) - matrix.get(j, i) * result.get(i));
            }
        }
        return result;
    }

    /**
     * Performs row permutation of a matrix with the given permutation vector
     *
     * @param matrix            matrix to be permuted
     * @param permutationVector vector with desired indices order
     * @return permuted matrix
     */
    public static Matrix permute(Matrix matrix, Vector permutationVector) {
        if (matrix.getRowDimension() != permutationVector.getDimension())
            throw new DimensionMismatchException(matrix.getRowDimension(), permutationVector.getDimension());

        Matrix result = matrix.copy();
        for (int i = 0, n = permutationVector.getDimension(); i < n; i++) {
            if (permutationVector.get(i) == i) continue;

            result.swapRows(i, (int) permutationVector.get(i));
        }
        return result;
    }

    /**
     * Performs permutation of a vector with the given permutation vector
     *
     * @param vector            vector to be permuted
     * @param permutationVector vector with desired indices order
     * @return permuted vector
     */
    public static Vector permute(Vector vector, Vector permutationVector) {
        LinearAlgebraUtil.checkDimensionsSame(vector, permutationVector);

        int dimension = vector.getDimension();

        Vector result = vector.copy();
        for (int i = 0; i < dimension; i++) {
            result.set(i, vector.get((int) permutationVector.get(i)));
        }
        return result;
    }
}
