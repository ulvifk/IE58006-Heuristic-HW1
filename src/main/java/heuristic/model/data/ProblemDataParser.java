package heuristic.model.data;

import static heuristic.JsonUtils.OBJECT_MAPPER;
import static heuristic.JsonUtils.PRETTY_WRITER;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProblemDataParser {

    private static final String CITIES_FIELD = "cities";
    private static final String INITIAL_TOUR_FIELD = "initial_tour";

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

        return PRETTY_WRITER.writeValueAsString(tree);
    }
}
