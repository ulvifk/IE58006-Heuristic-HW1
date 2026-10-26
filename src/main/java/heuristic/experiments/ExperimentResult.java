package heuristic.experiments;

import heuristic.model.SolverSolution;
import heuristic.solvers.simulatedannealing.SimulatedAnnealingConfig;
import lombok.Value;

/// One run as written to disk: the configuration it ran with and what the solver returned.
@Value
public class ExperimentResult {

    SimulatedAnnealingConfig config;
    SolverSolution solverSolution;
}
