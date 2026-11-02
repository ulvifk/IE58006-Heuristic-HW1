package heuristic.feasibility;

import static heuristic.feasibility.FeasibilityTestData.buildFeasibleSolution;
import static heuristic.feasibility.FeasibilityTestData.loadProblemData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;
import org.junit.jupiter.api.Test;

class TourCostCheckTest {

    private final ProblemData problemData = loadProblemData();

    @Test
    void passesWhenTheCostMatchesTheTourLength() {
        assertDoesNotThrow(() -> new TourCostCheck().check(buildFeasibleSolution(problemData), problemData));
    }

    @Test
    void raisesWhenTheCostDiffersFromTheTourLength() {
        var solution = new Solution(problemData.getInitialTour(), 90.0);

        var exception =
                assertThrows(IllegalArgumentException.class, () -> new TourCostCheck().check(solution, problemData));
        assertEquals("tour cost: the solution reports 90.000000, the tour is 94.150000 long", exception.getMessage());
    }
}
