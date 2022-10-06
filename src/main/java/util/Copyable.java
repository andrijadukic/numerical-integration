package util;

/**
 * Represents an object which can copy itself
 *
 * @param <T>
 */
public interface Copyable<T> {

    /**
     * Creates a deep copy of this object
     *
     * @return deep copy
     */
    T copy();
}
