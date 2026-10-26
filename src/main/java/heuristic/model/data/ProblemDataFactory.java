package heuristic.model.data;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProblemDataFactory {

    public static ProblemData build(List<City> cities, Tour initialTour) {
        return new ProblemData(cities, initialTour, DistanceMatrixFactory.build(cities));
    }
}
