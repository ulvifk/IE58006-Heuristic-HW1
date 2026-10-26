package heuristic.solvers.simulatedannealing;

import java.util.Locale;
import lombok.Value;

@Value
public class SimulatedAnnealingConfig {

    double initialTemperature; // T0
    double minimumTemperature; // Tmin; the search stops once T is at or below it
    double coolingRate; // alpha in T <- alpha * T
    int movesPerTemperature; // candidate moves between two cooling steps

    long seed;
    double timeLimitSeconds;

    // the parameters that differ between experiment runs; e.g. t0-3.00_alpha-0.90_seed-7
    public String getConfigKey() {
        return String.format(Locale.ROOT, "t0-%.2f_alpha-%.2f_seed-%d", initialTemperature, coolingRate, seed);
    }
}
