package heuristic.solvers.simulatedannealing;

import lombok.Value;

@Value
public class SimulatedAnnealingConfig {

    double initialTemperature; // T0
    double minimumTemperature; // Tmin; the search stops once T is at or below it
    double coolingRate; // alpha in T <- alpha * T
    int movesPerTemperature; // candidate moves between two cooling steps

    long seed;
    double timeLimitSeconds;
}
