package heuristic.experiments;

import static heuristic.JsonUtils.OBJECT_MAPPER;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExperimentResultParser {

    public static String toJson(ExperimentResult experimentResult) throws JsonProcessingException {
        return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(experimentResult);
    }
}
