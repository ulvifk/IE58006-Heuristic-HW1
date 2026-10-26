package heuristic.model.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class DistanceMatrix {

    @Getter
    private final double[][] distances; // [fromCityId][toCityId] -> Euclidean distance rounded to two decimals

    public double getDistance(int fromCityId, int toCityId) {
        return distances[fromCityId][toCityId];
    }
}
