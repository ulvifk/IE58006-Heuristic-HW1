package heuristic.feasibility;

import static heuristic.feasibility.FeasibilityTestData.buildFeasibleSolution;
import static heuristic.feasibility.FeasibilityTestData.buildSolution;
import static heuristic.feasibility.FeasibilityTestData.loadProblemData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import heuristic.model.data.ProblemData;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class StartCityCheckTest {

    private final ProblemData problemData = loadProblemData();

    @Test
    void passesWhenTheTourStartsAtCityZero() {
        assertDoesNotThrow(() -> new StartCityCheck().check(buildFeasibleSolution(problemData), problemData));
    }

    @Test
    void raisesWhenTheTourStartsElsewhere() {
        var cityIds = new ArrayList<>(problemData.getInitialTour().getCityIds());
        Collections.rotate(cityIds, -1);
        var solution = buildSolution(cityIds, problemData);

        var exception =
                assertThrows(IllegalArgumentException.class, () -> new StartCityCheck().check(solution, problemData));
        assertEquals("start city: the tour starts at city 4 instead of city 0", exception.getMessage());
    }
}
