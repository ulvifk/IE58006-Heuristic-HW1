package heuristic.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class DistanceMatrixFactoryTest {

    @Test
    void buildRoundsEachDistanceToTwoDecimals() {
        var distanceMatrix = DistanceMatrixFactory.build(List.of(new City(0, 0.5, 0.5), new City(1, 2.0, 0.8)));

        assertEquals(1.53, distanceMatrix.getDistance(0, 1));
        assertEquals(1.53, distanceMatrix.getDistance(1, 0));
        assertEquals(0.0, distanceMatrix.getDistance(0, 0));
    }
}
