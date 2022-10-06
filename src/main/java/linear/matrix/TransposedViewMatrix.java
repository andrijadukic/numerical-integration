package linear.matrix;

import linear.vector.Vector;

/**
 * Matrix class that serves as a view of the transposed matrix
 */
public class TransposedViewMatrix extends AbstractMatrix {

    private final Matrix view;

    public TransposedViewMatrix(Matrix view) {
        this.view = view;
    }

    @Override
    public Matrix copy() {
        return new TransposedViewMatrix(view.copy());
    }

    @Override
    public Matrix newInstance(int rows, int columns) {
        return view.newInstance(rows, columns);
    }

    @Override
    public int getRowDimension() {
        return view.getColumnDimension();
    }

    @Override
    public int getColumnDimension() {
        return view.getRowDimension();
    }

    @Override
    public double get(int i, int j) {
        return view.get(j, i);
    }

    @Override
    public Vector getRow(int index) {
        return view.getColumn(index);
    }

    @Override
    public Vector getColumn(int index) {
        return view.getRow(index);
    }

    @Override
    public Matrix set(int i, int j, double value) {
        return view.set(j, i, value);
    }

    @Override
    public Matrix transpose() {
        return view;
    }

    @Override
    public void swapRows(int i, int j) {
        view.swapColumns(i, j);
    }

    @Override
    public void swapColumns(int i, int j) {
        view.swapRows(i, j);
    }

    @Override
    public Vector[] columns() {
        return view.rows();
    }

    @Override
    public Vector[] rows() {
        return view.columns();
    }

    @Override
    public double[][] asArray() {
        int columnDimension = view.getColumnDimension();
        int rowDimension = view.getRowDimension();
        double[][] array = new double[columnDimension][rowDimension];
        for (int i = 0; i < columnDimension; i++) {
            for (int j = 0; j < rowDimension; j++) {
                array[i][j] = get(i, j);
            }
        }
        return array;
    }
}
