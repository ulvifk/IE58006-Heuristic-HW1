package heuristic.feasibility;

import static heuristic.feasibility.FeasibilityTestData.buildFeasibleSolution;
import static heuristic.feasibility.FeasibilityTestData.loadProblemData;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import heuristic.model.data.ProblemData;
import heuristic.model.data.Tour;
import heuristic.model.solution.Solution;
import heuristic.solvers.simulatedannealing.SimulatedAnnealingConfig;
import heuristic.solvers.simulatedannealing.SimulatedAnnealingFactory;
import java.util.List;
import org.junit.jupiter.api.Test;

class FeasibilityValidationTest {

    private final ProblemData problemData = loadProblemData();

    @Test
    void acceptsTheBaselineTour() {
        assertDoesNotThrow(() -> FeasibilityValidation.validate(buildFeasibleSolution(problemData), problemData));
    }

    @Test
    void acceptsTheBestSolutionOfABaselineRun() {
        var config = new SimulatedAnnealingConfig(3.0, 0.02, 0.90, 20, 7);
        var solution =
                SimulatedAnnealingFactory.build(config, problemData).solve().getSolution();

        assertDoesNotThrow(() -> FeasibilityValidation.validate(solution, problemData));
    }

    // violates the size, permutation, start city and cost checks; only the first one reports
    @Test
    void stopsAtTheFirstFailingCheck() {
        var solution = new Solution(new Tour(List.of(5, 5)), 0.0);

        var exception = assertThrows(
                IllegalArgumentException.class, () -> FeasibilityValidation.validate(solution, problemData));
        assertEquals("tour size: the tour visits 2 cities, the problem has 20", exception.getMessage());
    }
}
