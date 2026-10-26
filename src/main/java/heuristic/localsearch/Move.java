package heuristic.localsearch;

import heuristic.model.solution.Solution;

/// One candidate change to a solution; gain is positive when the move improves it.
public abstract class Move {

    // computed on the first request and kept, since acceptance and apply both need it
    private Double gain;

    public double getGain() {
        if (gain == null) {
            gain = computeGain();
        }

        return gain;
    }

    public abstract Solution apply();

    protected abstract double computeGain();
}
