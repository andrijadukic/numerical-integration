package util;

import java.util.AbstractList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class Sampling {

    private Sampling() {
    }

    public static <T extends Comparable<T>> int argMin(T[] array) {
        return arg(array, Comparator.naturalOrder());
    }

    public static <T extends Comparable<T>> int argMax(T[] array) {
        return arg(array, Comparator.reverseOrder());
    }

    public static <T> int arg(T[] array, Comparator<T> comparator) {
        int index = 0;
        T value = array[0];
        for (int i = 1, n = array.length; i < n; i++) {
            T temp = array[i];
            if (comparator.compare(temp, value) < 0) {
                value = temp;
                index = i;
            }
        }
        return index;
    }

    public static void cumulativeSum(double[] array, double bias) {
        double sum = 0.;
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = (sum += array[i] + bias);
        }
    }

    public static void scale(double[] array, double factor) {
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] /= factor;
        }
    }


    private static final int DEFAULT_LINSPACE_LEN = 50;

    public static List<Double> linspace(double from, double to) {
        return linspace(from, to, DEFAULT_LINSPACE_LEN);
    }

    public static List<Double> linspace(double from, double to, int length) {
        return new AbstractList<>() {

            private final double interval = (to - from) / (length - 1);

            @Override
            public Double get(int index) {
                return from + index * interval;
            }

            @Override
            public int size() {
                return length;
            }
        };
    }
}
