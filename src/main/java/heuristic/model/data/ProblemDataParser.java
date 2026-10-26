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
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProblemDataParser {

    private static final String CITIES_FIELD = "cities";
    private static final String INITIAL_TOUR_FIELD = "initial_tour";

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

    /// Reads the cities and the initial tour; the distance matrix is not stored and is rebuilt from the cities.
    public static ProblemData fromJson(String json) throws JsonProcessingException {
        var tree = OBJECT_MAPPER.readTree(json);
        var cityListType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, City.class);

        List<City> cities = OBJECT_MAPPER.treeToValue(tree.required(CITIES_FIELD), cityListType);
        var initialTour = OBJECT_MAPPER.treeToValue(tree.required(INITIAL_TOUR_FIELD), Tour.class);

        return ProblemDataFactory.build(cities, initialTour);
    }

    /// Writes the cities and the initial tour; the distance matrix is left out since it follows from the cities.
    public static String toJson(ProblemData problemData) throws JsonProcessingException {
        var tree = OBJECT_MAPPER.createObjectNode();
        tree.set(CITIES_FIELD, OBJECT_MAPPER.valueToTree(problemData.getCities()));
        tree.set(INITIAL_TOUR_FIELD, OBJECT_MAPPER.valueToTree(problemData.getInitialTour()));

        return OBJECT_MAPPER.writer(PRETTY_PRINTER).writeValueAsString(tree);
    }
}
