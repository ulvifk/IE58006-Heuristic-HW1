package heuristic;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.Separators;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/// The JSON settings every file in the project is read and written with: snake_case keys, strict reads, and
/// four-space indented writes.
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

    // strict on every value, since only the parser can reject a non-integer field in Java
    public static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .enable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES)
            .enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .disable(DeserializationFeature.ACCEPT_FLOAT_AS_INT)
            .disable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
            .build();

    // four-space indents and "key": value, as the instance files are written
    private static final DefaultIndenter INDENTER = new DefaultIndenter("    ", "\n");
    public static final ObjectWriter PRETTY_WRITER =
            OBJECT_MAPPER.writer(new DefaultPrettyPrinter(Separators.createDefaultInstance()
                            .withObjectFieldValueSpacing(Separators.Spacing.AFTER)
                            .withArrayEmptySeparator("")
                            .withObjectEmptySeparator(""))
                    .withObjectIndenter(INDENTER)
                    .withArrayIndenter(INDENTER));
}
