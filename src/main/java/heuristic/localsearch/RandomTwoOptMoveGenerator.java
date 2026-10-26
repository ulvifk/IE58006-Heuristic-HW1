package heuristic.localsearch;

import heuristic.model.data.DistanceMatrix;
import heuristic.model.solution.Solution;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;

/// Hands the consumer every 2-opt move of the solution once, in a random order, until the consumer stops the scan.
///
/// Each loop walks a shuffled copy of its range, so the first move is random and the scan still covers every move.
/// j starts at i + 2, since j = i + 1 reverses a single city.
@RequiredArgsConstructor
public class RandomTwoOptMoveGenerator implements MoveGenerator {

    private final DistanceMatrix distanceMatrix;

    // owned by the caller and seeded once, so a run is reproducible
    private final Random random;

    @Override
    public void forEachMove(Solution solution, MoveConsumer consumer) {
        var cityCount = solution.getTour().getCityIds().size();

        for (var i : getShuffledPositions(0, cityCount - 2)) {
            for (var j : getShuffledPositions(i + 2, cityCount)) {
                var move = new TwoOptMove(solution, distanceMatrix, i, j);
                if (!consumer.accept(move)) return;
            }
        }
    }

    // positions from..toExclusive - 1 in a random order
    private List<Integer> getShuffledPositions(int from, int toExclusive) {
        var positions =
                new ArrayList<>(IntStream.range(from, toExclusive).boxed().toList());
        Collections.shuffle(positions, random);

        return positions;
    }
}
