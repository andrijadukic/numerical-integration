package linear.vector;

import linear.matrix.Matrix;
import linear.matrix.ArrayMatrix;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Vector class which uses an array to store elements
 */
public class ArrayVector extends AbstractVector {

    private final double[] array;

    public ArrayVector(int dimension) {
        this(new double[dimension]);
    }

    public ArrayVector(double[] array) {
        this.array = Objects.requireNonNull(array);
    }

    public ArrayVector(int start, int end) {
        array = IntStream.range(start, end).asDoubleStream().toArray();
    }

    @Override
    public ArrayVector copy() {
        int dimension = getDimension();
        double[] copiedArray = new double[dimension];
        System.arraycopy(array, 0, copiedArray, 0, dimension);
        return new ArrayVector(copiedArray);
    }

    @Override
    public ArrayVector newInstance(int dimension) {
        return new ArrayVector(new double[dimension]);
    }

    @Override
    public int getDimension() {
        return array.length;
    }

    @Override
    public double get(int i) {
        return array[i];
    }

    @Override
    public ArrayVector set(int i, double value) {
        array[i] = value;
        return this;
    }

    @Override
    public Matrix asMatrix() {
        return new ArrayMatrix(new double[][]{array});
    }
}
