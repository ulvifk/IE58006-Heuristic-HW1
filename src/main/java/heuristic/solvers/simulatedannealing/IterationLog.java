package heuristic.solvers.simulatedannealing;

import heuristic.localsearch.TwoOptMove;
import heuristic.model.solution.Solution;
import lombok.Value;

/// What happened to one candidate move, for the iteration table and the length and temperature plots.
@Value
public class IterationLog {

    int iteration; // 1 for the first candidate
    double temperature; // T the candidate was judged at

    TwoOptMove move; // delta = -gain
    Double acceptanceProbability; // p = e^(-delta / T); null when the move is not worsening
    Double randomDraw; // u from U(0, 1); null when the move is not worsening
    boolean accepted;

    Solution current; // after the decision
    Solution best;
}
