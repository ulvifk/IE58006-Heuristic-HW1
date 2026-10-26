package heuristic.model;

import heuristic.model.solution.Solution;
import heuristic.solvers.simulatedannealing.IterationLog;
import java.util.List;
import lombok.Value;

/// What a solver hands back: the best solution and the record of how it got there.
@Value
public class SolverSolution {

    Solution solution;
    List<IterationLog> metaData; // [iteration - 1] -> what happened to that candidate
}
