package heuristic.feasibility;

import static heuristic.Constants.ZERO_TOLERANCE;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;
import heuristic.model.solution.SolutionFactory;

/// The stored cost matches the closed-tour length recomputed from the distance matrix; the solver only updates the
/// cost by each accepted move's delta, so this catches a wrong delta.
class TourCostCheck implements FeasibilityCheck {

    @Override
    public void check(Solution solution, ProblemData problemData) {
        var recomputedCost = SolutionFactory.build(solution.getTour(), problemData.getDistanceMatrix())
                .getCost();
        if (Math.abs(solution.getCost() - recomputedCost) <= ZERO_TOLERANCE) return;

        throw new IllegalArgumentException("tour cost: the solution reports %.6f, the tour is %.6f long"
                .formatted(solution.getCost(), recomputedCost));
    }
}
