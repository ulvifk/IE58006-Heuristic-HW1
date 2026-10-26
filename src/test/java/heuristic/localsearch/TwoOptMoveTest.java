package heuristic.localsearch;

import static heuristic.TourTestUtils.getClosedTourLength;
import static org.junit.jupiter.api.Assertions.assertEquals;

import heuristic.model.data.ProblemData;
import heuristic.model.data.ProblemDataParser;
import heuristic.model.solution.Solution;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwoOptMoveTest {

    private static final double TOLERANCE = 1e-9;

    private ProblemData problemData;
    private Solution baselineSolution;

    @BeforeEach
    void loadBaseline() throws IOException {
        problemData = ProblemDataParser.fromJson(Files.readString(Path.of("instances/tsp20.json")));
        baselineSolution = new Solution(problemData.getInitialTour(), 94.15);
    }

    // assignment A1: removes (4, 1) and (5, 15), adds (4, 5) and (1, 15); delta = 2.11 + 6.26 - 4.39 - 2.83
    @Test
    void getGainIsTheNegativeDeltaOfTheAssignmentMove() {
        var move = new TwoOptMove(baselineSolution, problemData.getDistanceMatrix(), 1, 9);

        assertEquals(-1.15, move.getGain(), TOLERANCE);
    }

    @Test
    void applyReversesTheSubpathAndUpdatesTheCost() {
        var move = new TwoOptMove(baselineSolution, problemData.getDistanceMatrix(), 1, 9);

        var solution = move.apply();

        assertEquals(
                List.of(0, 4, 5, 8, 6, 2, 9, 3, 7, 1, 15, 12, 18, 10, 16, 13, 11, 17, 14, 19),
                solution.getTour().getCityIds());
        assertEquals(95.30, solution.getCost(), TOLERANCE);
        assertEquals(
                getClosedTourLength(solution.getTour(), problemData.getDistanceMatrix()),
                solution.getCost(),
                TOLERANCE);
    }

    @Test
    void applyWrapsTheSecondEdgeToTheFirstCityAtTheLastPosition() {
        var move = new TwoOptMove(baselineSolution, problemData.getDistanceMatrix(), 3, 19);

        var solution = move.apply();

        assertEquals(
                getClosedTourLength(solution.getTour(), problemData.getDistanceMatrix()),
                solution.getCost(),
                TOLERANCE);
    }
}
