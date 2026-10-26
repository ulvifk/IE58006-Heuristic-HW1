package heuristic.solvers.simulatedannealing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import heuristic.model.data.ProblemData;
import heuristic.model.data.ProblemDataParser;
import heuristic.model.solution.SolutionFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimulatedAnnealingTest {

    private static final double BASELINE_COST = 94.15;

    private ProblemData problemData;

    @BeforeEach
    void loadInstance() throws IOException {
        problemData = ProblemDataParser.fromJson(Files.readString(Path.of("instances/tsp20.json")));
    }

    @Test
    void bestSolutionImprovesOnTheBaselineAndIsCostedCorrectly() {
        var best = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 60.0), problemData)
                .solve();

        assertTrue(best.getCost() < BASELINE_COST);
        assertEquals(
                SolutionFactory.build(best.getTour(), problemData.getDistanceMatrix())
                        .getCost(),
                best.getCost(),
                1e-9);
    }

    @Test
    void sameSeedGivesTheSameBestSolution() {
        var first = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 60.0), problemData)
                .solve();
        var second = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 60.0), problemData)
                .solve();

        assertEquals(first, second);
    }

    @Test
    void returnsTheInitialSolutionWhenTheTimeLimitIsZero() {
        var best = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 0.0), problemData)
                .solve();

        assertEquals(problemData.getInitialTour(), best.getTour());
    }

    private static SimulatedAnnealingConfig buildBaselineConfig(long seed, double timeLimitSeconds) {
        return new SimulatedAnnealingConfig(3.0, 0.02, 0.90, 20, seed, timeLimitSeconds);
    }
}
