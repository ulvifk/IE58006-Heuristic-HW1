package heuristic.localsearch;

import heuristic.model.solution.Solution;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class FakeMove extends Move {

    private final boolean feasible;
    private final double fakeGain;

    @Override
    public boolean isFeasible() {
        return feasible;
    }

    @Override
    public Solution apply() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected double computeGain() {
        return fakeGain;
    }
}
