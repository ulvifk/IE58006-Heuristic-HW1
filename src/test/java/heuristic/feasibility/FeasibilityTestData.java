package heuristic.feasibility;

import heuristic.model.data.ProblemData;
import heuristic.model.data.ProblemDataParser;
import heuristic.model.data.Tour;
import heuristic.model.solution.Solution;
import heuristic.model.solution.SolutionFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class FeasibilityTestData {

    @SneakyThrows
    static ProblemData loadProblemData() {
        return ProblemDataParser.fromJson(Files.readString(Path.of("instances/tsp20.json")));
    }

    // the baseline tour with its correct cost, 94.15
    static Solution buildFeasibleSolution(ProblemData problemData) {
        return SolutionFactory.build(problemData.getInitialTour(), problemData.getDistanceMatrix());
    }

    // the tour with its correct cost, so only the property under test is broken
    static Solution buildSolution(List<Integer> cityIds, ProblemData problemData) {
        return SolutionFactory.build(new Tour(cityIds), problemData.getDistanceMatrix());
    }
}
