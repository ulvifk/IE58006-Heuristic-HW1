package heuristic.localsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class FirstMoveConsumerTest {

    @Test
    void keepsScanningPastInfeasibleAndNonImprovingMoves() {
        var consumer = new FirstMoveConsumer();

        assertTrue(consumer.accept(new FakeMove(false, 9.0)));
        assertTrue(consumer.accept(new FakeMove(true, 0.0)));
        assertEquals(Optional.empty(), consumer.getMove());
    }

    @Test
    void stopsTheScanAtTheFirstImprovingMove() {
        var improving = new FakeMove(true, 1.0);
        var consumer = new FirstMoveConsumer();

        assertFalse(consumer.accept(improving));
        assertEquals(Optional.of(improving), consumer.getMove());
    }
}
