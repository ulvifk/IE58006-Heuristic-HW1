package heuristic;

import heuristic.experiments.ExperimentResult;
import heuristic.experiments.ExperimentResultParser;
import heuristic.experiments.ExperimentsConfigs;
import heuristic.model.data.ProblemData;
import heuristic.model.data.ProblemDataParser;
import heuristic.solvers.simulatedannealing.SimulatedAnnealingConfig;
import heuristic.solvers.simulatedannealing.SimulatedAnnealingFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/// Runs every experiment and writes one JSON file per run to experiments/<experiment>/, for the analysis script.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final Path INSTANCE_PATH = Path.of("instances", "tsp20.json");
    private static final Path OUTPUT_DIRECTORY = Path.of("experiments");

    public static void main(String[] args) throws IOException {
        var problemData = ProblemDataParser.fromJson(Files.readString(INSTANCE_PATH));

        runExperiment("baseline", ExperimentsConfigs.getBaselineConfigs(), problemData);
        runExperiment("c1_cooling_rate", ExperimentsConfigs.getCoolingRateConfigs(), problemData);
        runExperiment("c2_initial_temperature", ExperimentsConfigs.getInitialTemperatureConfigs(), problemData);
    }

    private static void runExperiment(
            String experimentName, List<SimulatedAnnealingConfig> configs, ProblemData problemData) throws IOException {
        var directory = OUTPUT_DIRECTORY.resolve(experimentName);
        Files.createDirectories(directory);

        for (var config : configs) {
            var solverSolution =
                    SimulatedAnnealingFactory.build(config, problemData).solve();

            var json = ExperimentResultParser.toJson(new ExperimentResult(config, solverSolution));
            Files.writeString(directory.resolve(config.getConfigKey() + ".json"), json);
        }

        LOGGER.info("{}: {} runs written to {}", experimentName, configs.size(), directory);
    }
}
