package heuristic.feasibility;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;

class TourSizeCheck implements FeasibilityCheck {

    @Override
    public void check(Solution solution, ProblemData problemData) {
        var tourSize = solution.getTour().getCityIds().size();
        var cityCount = problemData.getCities().size();
        if (tourSize == cityCount) return;

        throw new IllegalArgumentException(
                "tour size: the tour visits %d cities, the problem has %d".formatted(tourSize, cityCount));
    }
}
