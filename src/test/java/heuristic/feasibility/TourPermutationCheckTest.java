package heuristic.feasibility;

import static heuristic.feasibility.FeasibilityTestData.buildFeasibleSolution;
import static heuristic.feasibility.FeasibilityTestData.loadProblemData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import heuristic.model.data.ProblemData;
import heuristic.model.data.Tour;
import heuristic.model.solution.Solution;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class TourPermutationCheckTest {

    private final ProblemData problemData = loadProblemData();

    @Test
    void passesForAPermutationOfTheCities() {
        assertDoesNotThrow(() -> new TourPermutationCheck().check(buildFeasibleSolution(problemData), problemData));
    }

    @Test
    void raisesWhenACityIsVisitedTwice() {
        var solution = buildSolutionWithLastCity(0);

        var exception = assertThrows(
                IllegalArgumentException.class, () -> new TourPermutationCheck().check(solution, problemData));
        assertEquals("tour permutation: city 0 is visited twice", exception.getMessage());
    }

    @Test
    void raisesWhenACityIsNotInTheProblem() {
        var solution = buildSolutionWithLastCity(20);

        var exception = assertThrows(
                IllegalArgumentException.class, () -> new TourPermutationCheck().check(solution, problemData));
        assertEquals("tour permutation: city 20 is not in the problem", exception.getMessage());
    }

    // the cost is irrelevant here and the distance matrix has no row for an unknown city, so it is not computed
    private Solution buildSolutionWithLastCity(int lastCityId) {
        var cityIds = new ArrayList<>(problemData.getInitialTour().getCityIds());
        cityIds.set(cityIds.size() - 1, lastCityId);

        return new Solution(new Tour(cityIds), 0.0);
    }
}
