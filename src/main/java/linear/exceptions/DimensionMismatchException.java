package linear.exceptions;

/**
 * Exception class for general use when dimensions differ from expected
 */
public class DimensionMismatchException extends RuntimeException {

    public DimensionMismatchException(int real, int expected) {
        super("Expected dimension: " + expected + ", Received: " + real);
    }
}
