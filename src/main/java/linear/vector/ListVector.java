package linear.vector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Vector implementation that serves as a view of a list
 */
public class ListVector extends AbstractVector {

    private final List<Double> view;

    public ListVector(List<Double> view) {
        this.view = Objects.requireNonNull(view);
    }

    @Override
    public Vector newInstance(int dimension) {
        return new ListVector(new ArrayList<>(dimension));
    }

    @Override
    public Vector copy() {
        return new ListVector(new ArrayList<>(view));
    }

    @Override
    public int getDimension() {
        return view.size();
    }

    @Override
    public double get(int i) {
        return view.get(i);
    }

    @Override
    public Vector set(int i, double value) {
        view.set(i, value);
        return this;
    }

    @Override
    public Iterator<Double> iterator() {
        return view.iterator();
    }
}
