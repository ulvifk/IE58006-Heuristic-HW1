package heuristic.model.solution;

import heuristic.model.data.DistanceMatrix;
import heuristic.model.data.Tour;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SolutionFactory {

    /// Builds the solution of a tour, costed as the sum of the edges between consecutive cities plus the return to the
    /// first city; e.g. [0, 1, 2] -> d(0, 1) + d(1, 2) + d(2, 0).
    public static Solution build(Tour tour, DistanceMatrix distanceMatrix) {
        var cityIds = tour.getCityIds();
        var cost = 0.0;
        for (int position = 0; position < cityIds.size(); position++) {
            var nextCityId = cityIds.get((position + 1) % cityIds.size());
            cost += distanceMatrix.getDistance(cityIds.get(position), nextCityId);
        }

        return new Solution(tour, cost);
    }
}
