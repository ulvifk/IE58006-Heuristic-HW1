package heuristic.feasibility;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;
import java.util.HashSet;

/// Every city id in the tour is a city of the problem and appears once; with TourSizeCheck this makes the tour a
/// permutation of the cities.
class TourPermutationCheck implements FeasibilityCheck {

    @Override
    public void check(Solution solution, ProblemData problemData) {
        var cityCount = problemData.getCities().size();

        var visitedCityIds = new HashSet<Integer>();
        for (var cityId : solution.getTour().getCityIds()) {
            if (!isCityOfProblem(cityId, cityCount)) {
                throw new IllegalArgumentException("tour permutation: city %d is not in the problem".formatted(cityId));
            }
            if (!visitedCityIds.add(cityId)) {
                throw new IllegalArgumentException("tour permutation: city %d is visited twice".formatted(cityId));
            }
        }
    }

    private static boolean isCityOfProblem(int cityId, int cityCount) {
        if (cityId < 0) return false;
        if (cityId >= cityCount) return false;

        return true;
    }
}
