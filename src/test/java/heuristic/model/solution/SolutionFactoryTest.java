package heuristic.model.solution;

import static org.junit.jupiter.api.Assertions.assertEquals;

import heuristic.model.data.ProblemDataParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class SolutionFactoryTest {

    @Test
    void baselineTourHasTheLengthTheAssignmentStates() throws IOException {
        var problemData = ProblemDataParser.fromJson(Files.readString(Path.of("instances/tsp20.json")));

        var solution = SolutionFactory.build(problemData.getInitialTour(), problemData.getDistanceMatrix());

        assertEquals(94.15, solution.getCost(), 1e-9);
    }
}
