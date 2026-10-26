package heuristic.experiments;

import static heuristic.JsonUtils.PRETTY_WRITER;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExperimentResultParser {

    public static String toJson(ExperimentResult experimentResult) throws JsonProcessingException {
        return PRETTY_WRITER.writeValueAsString(experimentResult);
    }
}
