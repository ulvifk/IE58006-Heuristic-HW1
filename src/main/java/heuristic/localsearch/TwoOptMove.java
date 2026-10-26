package heuristic.localsearch;

import heuristic.model.data.DistanceMatrix;
import heuristic.model.data.Tour;
import heuristic.model.solution.Solution;
import java.util.ArrayList;
import java.util.Collections;
import lombok.RequiredArgsConstructor;

/// Replaces edges (t[i], t[i+1]) and (t[j], t[j+1]) with (t[i], t[j]) and (t[i+1], t[j+1]) by reversing positions
/// i+1..j; position j+1 wraps to 0 when j is the last position.
///
/// e.g. i = 1, j = 3: [0, 1] -> [2, 3] -> [4, 5]  becomes  [0, 1] -> [3, 2] -> [4, 5]
@RequiredArgsConstructor
public class TwoOptMove extends Move {

    private final Solution solution;
    private final DistanceMatrix distanceMatrix;

    private final int i;
    private final int j;

    @Override
    public Solution apply() {
        var cityIds = new ArrayList<>(solution.getTour().getCityIds());

        // subList is a view, so reversing it reverses positions i+1..j in place; e.g. with i = 1, j = 3,
        // edges 1-2 and 3-4 are swapped for 1-3 and 2-4 by reversing subList(2, 4):
        // [0, 1] -> [2, 3] -> [4, 5]  becomes  [0, 1] -> [3, 2] -> [4, 5]
        Collections.reverse(cityIds.subList(i + 1, j + 1));

        return new Solution(new Tour(cityIds), solution.getCost() - getGain());
    }

    // removed edges minus added edges, so the negative of the assignment's delta
    @Override
    protected double computeGain() {
        var cityIds = solution.getTour().getCityIds();
        var cityAtI = cityIds.get(i);
        var cityAfterI = cityIds.get(i + 1);
        var cityAtJ = cityIds.get(j);
        var cityAfterJ = cityIds.get((j + 1) % cityIds.size());

        var removedLength =
                distanceMatrix.getDistance(cityAtI, cityAfterI) + distanceMatrix.getDistance(cityAtJ, cityAfterJ);
        var addedLength =
                distanceMatrix.getDistance(cityAtI, cityAtJ) + distanceMatrix.getDistance(cityAfterI, cityAfterJ);

        return removedLength - addedLength;
    }
}
