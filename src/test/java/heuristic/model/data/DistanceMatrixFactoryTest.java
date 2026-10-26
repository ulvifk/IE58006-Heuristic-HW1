package heuristic.model.data;

import static heuristic.TourTestUtils.getClosedTourLength;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @Test
    void baselineTourHasTheLengthTheAssignmentStates() throws IOException {
        var problemData = ProblemDataParser.fromJson(Files.readString(Path.of("instances/tsp20.json")));
        var length = getClosedTourLength(problemData.getInitialTour(), problemData.getDistanceMatrix());

        assertEquals(94.15, length, 1e-9);
    }
}
