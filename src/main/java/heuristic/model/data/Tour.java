package heuristic.model.data;

import java.util.List;
import lombok.Value;

@Value
public class Tour {

    // [position] -> city id; each city appears once and the return to the first city is implied
    List<Integer> cityIds;
}
