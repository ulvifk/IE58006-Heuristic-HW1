package heuristic.solvers.simulatedannealing;

import static heuristic.Constants.ZERO_TOLERANCE;

import heuristic.localsearch.TwoOptMove;
import heuristic.model.data.DistanceMatrix;
import heuristic.model.solution.Solution;
import java.util.Random;

/// One simulated annealing run over random 2-opt moves; solve returns the best solution seen.
///
/// - each iteration draws one random 2-opt move of the current solution and accepts or rejects it
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
    private int candidateCount;

    public SimulatedAnnealing(
            SimulatedAnnealingConfig config, DistanceMatrix distanceMatrix, Random random, Solution initialSolution) {
        this.config = config;
        this.distanceMatrix = distanceMatrix;
        this.random = random;

        this.current = initialSolution;
        this.best = initialSolution;
        this.temperature = config.getInitialTemperature();
    }

    public Solution solve() {
        var deadlineNanos = System.nanoTime() + (long) (config.getTimeLimitSeconds() * 1e9);

        while (!shouldStop(deadlineNanos)) {
            var move = drawRandomMove();
            candidateCount++;
            if (isAccepted(move)) {
                current = move.apply();
            }

            if (current.getCost() < best.getCost() - ZERO_TOLERANCE) {
                best = current;
            }

            // T <- alpha * T after every movesPerTemperature candidates
            if (candidateCount % config.getMovesPerTemperature() == 0) {
                temperature *= config.getCoolingRate();
            }
        }

        return best;
    }

    private boolean shouldStop(long deadlineNanos) {
        if (temperature <= config.getMinimumTemperature()) return true;
        if (System.nanoTime() >= deadlineNanos) return true;

        return false;
    }

    /// Draws positions i < j of a 2-opt move, with j >= i + 2 since j = i + 1 reverses a single city.
    private TwoOptMove drawRandomMove() {
        var cityCount = current.getTour().getCityIds().size();
        var i = random.nextInt(0, cityCount - 2);
        var j = random.nextInt(i + 2, cityCount);

        return new TwoOptMove(current, distanceMatrix, i, j);
    }

    /// Accepts a non-worsening move (delta <= 0) outright; draws u from U(0, 1) only for a worsening one and accepts
    /// it when u < e^(-delta / T).
    private boolean isAccepted(TwoOptMove move) {
        var delta = -move.getGain();
        if (delta <= ZERO_TOLERANCE) return true;

        var acceptanceProbability = Math.exp(-delta / temperature);
        return random.nextDouble() < acceptanceProbability;
    }
}
