package heuristic.feasibility;

import heuristic.model.data.ProblemData;
import heuristic.model.solution.Solution;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FeasibilityValidation {

    private static final List<FeasibilityCheck> CHECKS =
            List.of(new TourSizeCheck(), new TourPermutationCheck(), new StartCityCheck(), new TourCostCheck());

    public static void validate(Solution solution, ProblemData problemData) {
        for (var check : CHECKS) {
            check.check(solution, problemData);
        }
    }
}
