package integration.util;

public final class NthIterationObserver implements LinearSystemIntegrationObserver {

    private final LinearSystemIntegrationObserver observer;
    private final int step;

    public NthIterationObserver(LinearSystemIntegrationObserver observer, int step) {
        this.observer = observer;
        this.step = step;
    }

    @Override
    public void update(StateStatistics statistics) {
        if (statistics.iteration() % step == 0) {
            observer.update(statistics);
        }
    }
}
