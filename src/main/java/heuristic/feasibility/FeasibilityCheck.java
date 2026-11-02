package heuristic.feasibility;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;

/// Throws an IllegalArgumentException, its message starting with the check's topic, when the solution violates the
/// check.
interface FeasibilityCheck {

    void check(Solution solution, ProblemData problemData);
}
