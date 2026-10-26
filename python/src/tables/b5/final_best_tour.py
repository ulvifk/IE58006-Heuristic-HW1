from typing import ClassVar

from data.solution_set import ExperimentResult
from tables.table_format import TableFormat


class FinalBestTour:
    """
    B5: the best tour of one run in a grey box, as a closed city sequence with its
    length below; e.g. "Final best tour: / 0 -> 7 -> ... -> 1 -> 0 / Length: 42.50".
    """

    FILE_NAME: ClassVar[str] = "final_best_tour"

    @staticmethod
    def write(result: ExperimentResult) -> None:
        best = result.solver_solution.solution
        city_ids = best.tour.city_ids
        closed_tour = " \\to ".join(
            str(city_id) for city_id in (*city_ids, city_ids[0])
        )

        latex = (
            "\\noindent\\colorbox{gray!15}{\\parbox{\\dimexpr\\linewidth-2\\fboxsep}{\n"
            "Final best tour:\\\\\n"
            f"${closed_tour}$\\\\\n"
            f"Length: {TableFormat.format_length(best.cost)}\n"
            "}}\n"
        )
        TableFormat.write_latex(FinalBestTour.FILE_NAME, latex)
