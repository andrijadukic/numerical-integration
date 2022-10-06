package linear.exceptions;

/**
 * Exception class used when given matrix is not a square matrix
 */
public class NonSquareMatrixException extends RuntimeException {

    public NonSquareMatrixException(int rowDimension, int columnDimension) {
        super("Matrix of dimensions " + rowDimension + "x" + columnDimension + " is not a square matrix!");
    }
}
