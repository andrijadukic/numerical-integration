package integration.util;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLinearSystemIntegrationSubject implements LinearSystemIntegrationSubject {

    private final List<LinearSystemIntegrationObserver> observers;

    protected AbstractLinearSystemIntegrationSubject() {
        observers = new ArrayList<>();
    }

    @Override
    public final void addObserver(LinearSystemIntegrationObserver observer) {
        observers.add(observer);
    }

    @Override
    public final void removeObserver(LinearSystemIntegrationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void clearObservers() {
        observers.clear();
    }

    @Override
    public final void notifyObservers(StateStatistics statistics) {
        observers.forEach(observer -> observer.update(statistics));
    }
}
