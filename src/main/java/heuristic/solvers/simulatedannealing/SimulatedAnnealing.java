package heuristic.solvers.simulatedannealing;

import static heuristic.Constants.ZERO_TOLERANCE;

import heuristic.localsearch.TwoOptMove;
import heuristic.model.SolverSolution;
import heuristic.model.data.DistanceMatrix;
import heuristic.model.solution.Solution;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/// One simulated annealing run over random 2-opt moves; solve returns the best solution seen with a log of every
/// candidate.
///
/// - each iteration draws one random 2-opt move of the current solution and accepts or rejects it
/// - a non-worsening move (delta <= 0) is accepted outright; a worsening one when u < p = e^(-delta / T)
/// - the temperature is multiplied by the cooling rate after every movesPerTemperature candidates
/// - stops when the temperature reaches the minimum or the time limit passes
/// - the best solution only changes when the current one beats it
public class SimulatedAnnealing {

    private final SimulatedAnnealingConfig config;
    private final DistanceMatrix distanceMatrix;
    private final Random random; // seeded from the config, so a run is reproducible

    private Solution current;
    private Solution best;
    private double temperature;

    private final List<IterationLog> iterationLogs; // [iteration - 1] -> what happened to that candidate

    public SimulatedAnnealing(
            SimulatedAnnealingConfig config, DistanceMatrix distanceMatrix, Random random, Solution initialSolution) {
        this.config = config;
        this.distanceMatrix = distanceMatrix;
        this.random = random;

        this.current = initialSolution;
        this.best = initialSolution;
        this.temperature = config.getInitialTemperature();
        this.iterationLogs = new ArrayList<>();
    }

    public SolverSolution solve() {
        var deadlineNanos = System.nanoTime() + (long) (config.getTimeLimitSeconds() * 1e9);

        while (!shouldStop(deadlineNanos)) {
            var move = drawRandomMove();
            var delta = -move.getGain();

            Double acceptanceProbability = null;
            Double randomDraw = null;
            var accepted = true;
            if (delta > ZERO_TOLERANCE) {
                acceptanceProbability = Math.exp(-delta / temperature);
                randomDraw = random.nextDouble();
                accepted = randomDraw < acceptanceProbability;
            }

            if (accepted) {
                current = move.apply();
            }
            if (current.getCost() < best.getCost() - ZERO_TOLERANCE) {
                best = current;
            }

            log(move, delta, acceptanceProbability, randomDraw, accepted);

            // T <- alpha * T after every movesPerTemperature candidates
            if (iterationLogs.size() % config.getMovesPerTemperature() == 0) {
                temperature *= config.getCoolingRate();
            }
        }

        return new SolverSolution(best, iterationLogs);
    }

    private boolean shouldStop(long deadlineNanos) {
        if (temperature <= config.getMinimumTemperature()) return true;
        if (System.nanoTime() >= deadlineNanos) return true;

        return false;
    }

    // records the candidate at the current temperature, with the current and best solutions after the decision
    private void log(TwoOptMove move, double delta, Double acceptanceProbability, Double randomDraw, boolean accepted) {
        var iteration = iterationLogs.size() + 1;
        iterationLogs.add(new IterationLog(
                iteration,
                temperature,
                move.getI(),
                move.getJ(),
                delta,
                acceptanceProbability,
                randomDraw,
                accepted,
                current,
                best));
    }

    /// Draws positions i < j of a 2-opt move, with j >= i + 2 since j = i + 1 reverses a single city.
    private TwoOptMove drawRandomMove() {
        var cityCount = current.getTour().getCityIds().size();
        var i = random.nextInt(0, cityCount - 2);
        var j = random.nextInt(i + 2, cityCount);

        return new TwoOptMove(current, distanceMatrix, i, j);
    }
}
