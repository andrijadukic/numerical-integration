package integration.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StateCollector implements LinearSystemIntegrationObserver {

    private final List<StateStatistics> statistics;

    public StateCollector() {
        statistics = new ArrayList<>();
    }

    @Override
    public void update(StateStatistics statistic) {
        statistics.add(statistic);
    }

    public void clear() {
        statistics.clear();
    }

    public List<StateStatistics> getStatistics() {
        return Collections.unmodifiableList(statistics);
    }
}
