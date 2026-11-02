package heuristic.feasibility;

import static heuristic.feasibility.FeasibilityTestData.buildFeasibleSolution;
import static heuristic.feasibility.FeasibilityTestData.buildSolution;
import static heuristic.feasibility.FeasibilityTestData.loadProblemData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import heuristic.model.data.ProblemData;
import java.util.List;
import org.junit.jupiter.api.Test;

class TourSizeCheckTest {

    private final ProblemData problemData = loadProblemData();

    @Test
    void passesWhenTheTourVisitsEveryCity() {
        assertDoesNotThrow(() -> new TourSizeCheck().check(buildFeasibleSolution(problemData), problemData));
    }

    @Test
    void raisesWhenTheTourMissesCities() {
        var solution = buildSolution(List.of(0, 1, 2), problemData);

        var exception =
                assertThrows(IllegalArgumentException.class, () -> new TourSizeCheck().check(solution, problemData));
        assertTrue(exception.getMessage().startsWith("tour size"));
    }
}
