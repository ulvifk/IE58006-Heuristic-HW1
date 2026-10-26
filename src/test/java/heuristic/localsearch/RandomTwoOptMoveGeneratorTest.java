package heuristic.localsearch;

import static heuristic.TourTestUtils.getClosedTourLength;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import heuristic.model.data.ProblemData;
import heuristic.model.data.ProblemDataParser;
import heuristic.model.data.Tour;
import heuristic.model.solution.Solution;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RandomTwoOptMoveGeneratorTest {

    // 20 cities: (n - 1)(n - 2) / 2 pairs with j >= i + 2
    private static final int ALL_MOVE_COUNT = 171;

    private ProblemData problemData;
    private Solution baselineSolution;

    @BeforeEach
    void loadBaseline() throws IOException {
        problemData = ProblemDataParser.fromJson(Files.readString(Path.of("instances/tsp20.json")));
        baselineSolution = new Solution(problemData.getInitialTour(), 94.15);
    }

    @Test
    void stopsWhenTheConsumerStopsTheScan() {
        var moves = generateMoves(new Random(7), 10);

        assertEquals(10, moves.size());
    }

    @Test
    void handsEveryMoveExactlyOnce() {
        var moves = generateMoves(new Random(7), Integer.MAX_VALUE);

        assertEquals(ALL_MOVE_COUNT, moves.size());
        assertEquals(ALL_MOVE_COUNT, new HashSet<>(getAppliedTours(moves)).size());
    }

    @Test
    void everyMoveChangesTheTourAndKeepsTheCostConsistent() {
        for (var move : generateMoves(new Random(7), Integer.MAX_VALUE)) {
            var solution = move.apply();

            assertNotEquals(baselineSolution.getTour(), solution.getTour());
            assertEquals(
                    getClosedTourLength(solution.getTour(), problemData.getDistanceMatrix()), solution.getCost(), 1e-9);
        }
    }

    @Test
    void sameSeedGivesTheSameMoves() {
        var firstTours = getAppliedTours(generateMoves(new Random(7), Integer.MAX_VALUE));
        var secondTours = getAppliedTours(generateMoves(new Random(7), Integer.MAX_VALUE));

        assertEquals(firstTours, secondTours);
    }

    private List<Move> generateMoves(Random random, int maxMoveCount) {
        var generator = new RandomTwoOptMoveGenerator(problemData.getDistanceMatrix(), random);

        var moves = new ArrayList<Move>();
        generator.forEachMove(baselineSolution, move -> {
            moves.add(move);
            return moves.size() < maxMoveCount;
        });

        return moves;
    }

    private static List<Tour> getAppliedTours(List<Move> moves) {
        var tours = new ArrayList<Tour>();
        for (var move : moves) {
            tours.add(move.apply().getTour());
        }

        return tours;
    }
}
