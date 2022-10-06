package linear.matrix;

import linear.linalg.LinearAlgebra;
import linear.vector.Vector;

import java.util.Iterator;
import java.util.function.DoublePredicate;

/**
 * Abstract Matrix class from which all IMatrix implementations should inherit
 */
public abstract class AbstractMatrix implements Matrix {

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        return LinearAlgebra.anyMatch(elementIterator(), predicate);
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        return LinearAlgebra.allMatch(elementIterator(), predicate);
    }

    @Override
    public Iterator<Vector> iterator() {
        return new AbstractMatrixIterator(this);
    }

    @Override
    public Iterator<Double> elementIterator() {
        return new AbstractMatrixElementIterator(this);
    }

    @Override
    public Matrix transpose() {
        return new TransposedViewMatrix(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Matrix other)) return false;

        int rowDimension = getRowDimension();
        int columnDimension = getColumnDimension();

        if (rowDimension != other.getRowDimension() || columnDimension != other.getColumnDimension())
            return false;

        for (int i = 0; i < rowDimension; i++) {
            for (int j = 0; j < columnDimension; j++) {
                if (get(i, j) != other.get(i, j)) return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder matrix = new StringBuilder();

        int m = getColumnDimension() - 1;
        for (int i = 0, n = getRowDimension(); i < n; i++) {
            for (int j = 0; j < m; j++) {
                matrix.append(get(i, j)).append(" ");
            }
            matrix.append(get(i, m));
            matrix.append(System.lineSeparator());
        }

        return matrix.toString();
    }

    private static class AbstractMatrixIterator implements Iterator<Vector> {

        private final Matrix matrix;
        private final int rowDimension;

        private int rowCount;

        public AbstractMatrixIterator(Matrix matrix) {
            this.matrix = matrix;
            rowDimension = matrix.getRowDimension();
        }

        @Override
        public boolean hasNext() {
            return rowCount < rowDimension;
        }

        @Override
        public Vector next() {
            return matrix.getRow(rowCount++);
        }
    }

    private static class AbstractMatrixElementIterator implements Iterator<Double> {

        private final Matrix matrix;
        private final int rowDimension;
        private final int columnDimension;

        private int rowCount;
        private int columnCount;

        public AbstractMatrixElementIterator(Matrix matrix) {
            this.matrix = matrix;
            rowDimension = matrix.getRowDimension();
            columnDimension = matrix.getColumnDimension();
        }

        @Override
        public boolean hasNext() {
            return rowCount < rowDimension || columnCount < columnDimension;
        }

        @Override
        public Double next() {
            double value = matrix.get(rowCount, columnCount++);

            if (columnCount == columnDimension - 1) {
                rowCount++;
            }

            return value;
        }
    }
}