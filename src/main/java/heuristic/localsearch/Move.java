package heuristic.localsearch;

import heuristic.model.data.Tour;

/// One candidate change to a tour; gain is positive when the move improves it.
public abstract class Move {

    // computed on the first request and kept, since feasibility and acceptance both need it
    private Double gain;

    public double getGain() {
        if (gain == null) {
            gain = computeGain();
        }

        return gain;
    }

    public abstract boolean isFeasible();

    public abstract Tour apply();

    protected abstract double computeGain();
}
