package heuristic;

import heuristic.model.data.DistanceMatrix;
import heuristic.model.data.Tour;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TourTestUtils {

    // sum of the edges between consecutive cities, including the return to the first city
    public static double getClosedTourLength(Tour tour, DistanceMatrix distanceMatrix) {
        var cityIds = tour.getCityIds();
        var length = 0.0;
        for (int position = 0; position < cityIds.size(); position++) {
            var nextCityId = cityIds.get((position + 1) % cityIds.size());
            length += distanceMatrix.getDistance(cityIds.get(position), nextCityId);
        }

        return length;
    }
}
