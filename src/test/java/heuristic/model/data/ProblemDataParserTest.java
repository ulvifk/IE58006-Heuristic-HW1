package heuristic.model.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProblemDataParserTest {

    private static final String JSON = """
            {
                "cities": [
                    {"id": 0, "x": 0.5, "y": 0.5},
                    {"id": 1, "x": 2.0, "y": 0.8}
                ],
                "initial_tour": {"city_ids": [0, 1]}
            }
            """;

    @Test
    void fromJsonReconstructsProblemData() throws JsonProcessingException {
        assertEquals(buildProblemData(), ProblemDataParser.fromJson(JSON));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1.5", "1.0", "null", "\"1\""})
    void fromJsonRejectsACityIdThatIsNotAnInteger(String id) {
        var json = JSON.replace("\"id\": 1", "\"id\": " + id);

        assertThrows(JsonProcessingException.class, () -> ProblemDataParser.fromJson(json));
    }

    private static ProblemData buildProblemData() {
        return ProblemDataFactory.build(List.of(new City(0, 0.5, 0.5), new City(1, 2.0, 0.8)), new Tour(List.of(0, 1)));
    }
}
