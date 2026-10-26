package heuristic.solvers.simulatedannealing;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.SolutionFactory;
import java.util.Random;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SimulatedAnnealingFactory {

    /// Builds a run that starts from the problem's initial tour, with its random numbers seeded from the config.
    public static SimulatedAnnealing build(SimulatedAnnealingConfig config, ProblemData problemData) {
        var random = new Random(config.getSeed());
        var initialSolution = SolutionFactory.build(problemData.getInitialTour(), problemData.getDistanceMatrix());

        return new SimulatedAnnealing(config, problemData, random, initialSolution);
    }
}
