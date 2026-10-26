package heuristic.model.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProblemDataParser {

    // strict on every value, since only the parser can reject a non-integer field in Java
    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
            .disable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
            .build();

    // four-space indents and "key": value, as the instance files are written
    private static final DefaultIndenter INDENTER = new DefaultIndenter("    ", "\n");
    private static final DefaultPrettyPrinter PRETTY_PRINTER = new DefaultPrettyPrinter(
                    Separators.createDefaultInstance()
                            .withObjectFieldValueSpacing(Separators.Spacing.AFTER)
                            .withArrayEmptySeparator("")
                            .withObjectEmptySeparator(""))
            .withObjectIndenter(INDENTER)
            .withArrayIndenter(INDENTER);

    public static ProblemData fromJson(String json) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(json, ProblemData.class);
    }

    public static String toJson(ProblemData problemData) throws JsonProcessingException {
        return OBJECT_MAPPER.writer(PRETTY_PRINTER).writeValueAsString(problemData);
    }
}
