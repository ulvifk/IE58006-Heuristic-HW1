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
                .solve()
                .getSolution();

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
                .solve()
                .getSolution();
        var second = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 60.0), problemData)
                .solve()
                .getSolution();

        assertEquals(first, second);
    }

    @Test
    void returnsTheInitialSolutionWhenTheTimeLimitIsZero() {
        var best = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 0.0), problemData)
                .solve()
                .getSolution();

        assertEquals(problemData.getInitialTour(), best.getTour());
    }

    // T reaches Tmin = 0.02 after 48 cooling steps, since 3.0 * 0.9^47 > 0.02 >= 3.0 * 0.9^48; 48 * 20 candidates
    @Test
    void logsEveryCandidateUntilTheTemperatureReachesTheMinimum() {
        var simulatedAnnealing = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 60.0), problemData);
        var solverSolution = simulatedAnnealing.solve();
        var best = solverSolution.getSolution();

        var iterationLogs = solverSolution.getMetaData();
        assertEquals(960, iterationLogs.size());
        assertEquals(1, iterationLogs.getFirst().getIteration());
        assertEquals(3.0, iterationLogs.getFirst().getTemperature());
        assertEquals(2.7, iterationLogs.get(20).getTemperature(), 1e-9);
        assertEquals(best, iterationLogs.getLast().getBest());
    }

    @Test
    void logsAcceptanceDecisionsConsistentWithTheMoves() {
        var simulatedAnnealing = SimulatedAnnealingFactory.build(buildBaselineConfig(7, 60.0), problemData);
        var iterationLogs = simulatedAnnealing.solve().getMetaData();

        var previousCurrentCost = 94.15;
        var previousBestCost = 94.15;
        for (var iterationLog : iterationLogs) {
            var currentCost = iterationLog.getCurrent().getCost();
            var bestCost = iterationLog.getBest().getCost();

            if (iterationLog.getAcceptanceProbability() == null) {
                assertTrue(iterationLog.isAccepted());
            } else {
                assertEquals(
                        iterationLog.getRandomDraw() < iterationLog.getAcceptanceProbability(),
                        iterationLog.isAccepted());
            }
            var expectedCurrentCost =
                    iterationLog.isAccepted() ? previousCurrentCost + iterationLog.getDelta() : previousCurrentCost;
            assertEquals(expectedCurrentCost, currentCost, 1e-9);
            assertTrue(bestCost <= previousBestCost);
            assertEquals(Math.min(previousBestCost, currentCost), bestCost, 1e-9);

            previousCurrentCost = currentCost;
            previousBestCost = bestCost;
        }
    }

    private static SimulatedAnnealingConfig buildBaselineConfig(long seed, double timeLimitSeconds) {
        return new SimulatedAnnealingConfig(3.0, 0.02, 0.90, 20, seed, timeLimitSeconds);
    }
}
