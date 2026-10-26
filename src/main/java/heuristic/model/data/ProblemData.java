package heuristic.model.data;

import java.util.List;
import lombok.Value;

@Value
public class ProblemData {

    // [cityId] -> city
    List<City> cities;

    // the baseline tour every run starts from
    Tour initialTour;

    DistanceMatrix distanceMatrix;
}
