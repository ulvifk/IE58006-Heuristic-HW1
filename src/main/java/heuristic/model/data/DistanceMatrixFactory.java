package heuristic.model.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DistanceMatrixFactory {

    /// Builds the distances between every pair of cities, each rounded to two decimals before any tour sums them,
    /// as the assignment defines them; e.g. (0.5, 0.5) to (2.0, 0.8) -> sqrt(2.34) = 1.5297 -> 1.53.
    public static DistanceMatrix build(List<City> cities) {
        var distances = new double[cities.size()][cities.size()];
        for (var from : cities) {
            for (var to : cities) {
                distances[from.getId()][to.getId()] = getRoundedDistance(from, to);
            }
        }

        return new DistanceMatrix(distances);
    }

    private static double getRoundedDistance(City from, City to) {
        var distance = Math.hypot(from.getX() - to.getX(), from.getY() - to.getY());
        return Math.round(distance * 100) / 100.0;
    }
}
