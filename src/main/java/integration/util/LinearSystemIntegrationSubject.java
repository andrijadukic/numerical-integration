package integration.util;

public interface LinearSystemIntegrationSubject {

    void addObserver(LinearSystemIntegrationObserver observer);

    void removeObserver(LinearSystemIntegrationObserver observer);

    void clearObservers();

    void notifyObservers(StateStatistics statistics);
}
