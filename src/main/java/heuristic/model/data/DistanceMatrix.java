package heuristic.model.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistanceMatrix {

    // [fromCityId][toCityId] -> Euclidean distance rounded to two decimals
    private final double[][] distances;

    public double getDistance(int fromCityId, int toCityId) {
        return distances[fromCityId][toCityId];
    }
}
