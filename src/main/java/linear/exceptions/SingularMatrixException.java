package linear.exceptions;

/**
 * Exception class used when given matrix is singular
 */
public class SingularMatrixException extends RuntimeException {

    public SingularMatrixException() {
        super("Matrix is singular");
    }
}
