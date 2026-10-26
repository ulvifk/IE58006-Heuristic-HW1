package heuristic.localsearch;

import static heuristic.Constants.ZERO_TOLERANCE;

import java.util.Optional;

/// Keeps the first feasible move whose gain beats the tolerance and stops the scan there.
public class FirstMoveConsumer implements MoveConsumer {

    private Move move;

    @Override
    public boolean accept(Move move) {
        if (!move.isFeasible()) return true;
        if (move.getGain() <= ZERO_TOLERANCE) return true;

        this.move = move;

        return false;
    }

    // empty when the scan found no improving move
    public Optional<Move> getMove() {
        return Optional.ofNullable(move);
    }
}
