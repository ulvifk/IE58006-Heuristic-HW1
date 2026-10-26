package heuristic.localsearch;

import heuristic.model.solution.Solution;

public interface MoveGenerator {

    // builds one move at a time and hands it to the consumer; returns as soon as accept answers false, so it never
    // enumerates more moves than the consumer asked for. Never build the full list.
    void forEachMove(Solution solution, MoveConsumer consumer);
}
