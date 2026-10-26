package heuristic.model.solution;

import heuristic.model.data.Tour;
import lombok.Value;

@Value
public class Solution {

    Tour tour;

    // closed-tour length, including the return to the first city
    double cost;
}
