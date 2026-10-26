package heuristic.localsearch;

public interface MoveConsumer {

    // returns whether the generator should keep scanning
    boolean accept(Move move);
}
