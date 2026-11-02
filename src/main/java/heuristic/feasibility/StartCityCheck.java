package heuristic.feasibility;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;

/// The tour starts at the first city of the initial tour, city 0, which the assignment closes every tour back to.
class StartCityCheck implements FeasibilityCheck {

    @Override
    public void check(Solution solution, ProblemData problemData) {
        int startCityId = solution.getTour().getCityIds().getFirst();
        int expectedStartCityId = problemData.getInitialTour().getCityIds().getFirst();
        if (startCityId == expectedStartCityId) return;

        throw new IllegalArgumentException("start city: the tour starts at city %d instead of city %d"
                .formatted(startCityId, expectedStartCityId));
    }
}
