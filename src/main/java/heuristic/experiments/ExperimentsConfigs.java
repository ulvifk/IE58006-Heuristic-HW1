package heuristic.experiments;

import heuristic.solvers.simulatedannealing.SimulatedAnnealingConfig;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/// The runs of each experiment. Part C runs every configuration under test once per seed 0..9, so all configurations
/// see the same seeds and compare fairly.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExperimentsConfigs {

    /// B5: the baseline parameters with seed 7; 1 run.
    public static List<SimulatedAnnealingConfig> getBaselineConfigs() {
        var initialTemperature = 3.0;
        var minimumTemperature = 0.02;
        var coolingRate = 0.90;
        var movesPerTemperature = 20;
        var seed = 7;

        return List.of(new SimulatedAnnealingConfig(
                initialTemperature, minimumTemperature, coolingRate, movesPerTemperature, seed));
    }

    /// C1: alpha in {0.80, 0.90, 0.95, 0.99}; 4 alphas x 10 seeds.
    public static List<SimulatedAnnealingConfig> getCoolingRateConfigs() {
        var coolingRates = List.of(0.80, 0.90, 0.95, 0.99);
        var initialTemperature = 3.0;
        var minimumTemperature = 0.02;
        var movesPerTemperature = 20;
        var seedCount = 10;

        var configs = new ArrayList<SimulatedAnnealingConfig>();
        for (var coolingRate : coolingRates) {
            for (long seed = 0; seed < seedCount; seed++) {
                configs.add(new SimulatedAnnealingConfig(
                        initialTemperature, minimumTemperature, coolingRate, movesPerTemperature, seed));
            }
        }

        return configs;
    }

    /// C2: T0 in {1.0, 3.0, 6.0}; 3 T0s x 10 seeds.
    public static List<SimulatedAnnealingConfig> getInitialTemperatureConfigs() {
        var initialTemperatures = List.of(1.0, 3.0, 6.0);
        var minimumTemperature = 0.02;
        var coolingRate = 0.90;
        var movesPerTemperature = 20;
        var seedCount = 10;

        var configs = new ArrayList<SimulatedAnnealingConfig>();
        for (var initialTemperature : initialTemperatures) {
            for (long seed = 0; seed < seedCount; seed++) {
                configs.add(new SimulatedAnnealingConfig(
                        initialTemperature, minimumTemperature, coolingRate, movesPerTemperature, seed));
            }
        }

        return configs;
    }

    /// C3: the T0 and alpha chosen from C1 and C2; 10 seeds.
    public static List<SimulatedAnnealingConfig> getRecommendedConfigs(double initialTemperature, double coolingRate) {
        var minimumTemperature = 0.02;
        var movesPerTemperature = 20;
        var seedCount = 10;

        var configs = new ArrayList<SimulatedAnnealingConfig>();
        for (long seed = 0; seed < seedCount; seed++) {
            configs.add(new SimulatedAnnealingConfig(
                    initialTemperature, minimumTemperature, coolingRate, movesPerTemperature, seed));
        }

        return configs;
    }
}
