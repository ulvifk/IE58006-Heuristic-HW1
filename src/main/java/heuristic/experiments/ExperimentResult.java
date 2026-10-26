package heuristic.experiments;

import heuristic.model.SolverSolution;
import heuristic.model.data.ProblemData;
import heuristic.solvers.simulatedannealing.SimulatedAnnealingConfig;
import lombok.Value;

/// One run as written to disk: the instance it solved, the configuration it ran with and what the solver returned.
@Value
public class ExperimentResult {

    ProblemData problemData;
    SimulatedAnnealingConfig config;
    SolverSolution solverSolution;
}
