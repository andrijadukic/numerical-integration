package util;

import java.util.function.DoublePredicate;

/**
 * Represents a collection of real numbers (double precision) which can apply common predicate methods to its elements
 */
public interface Matchable {

    /**
     * Tests if any element matches predicate
     *
     * @param predicate predicate to be tested
     * @return true if any element matches, false otherwise
     */
    boolean anyMatch(DoublePredicate predicate);


    /**
     * Tests if all elements match predicate
     *
     * @param predicate predicate to be tested
     * @return true if all elements match, false otherwise
     */
    boolean allMatch(DoublePredicate predicate);
}
