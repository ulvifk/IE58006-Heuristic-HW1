from typing import ClassVar

import pandas as pd

from data.solution_set import ExperimentResult
from tables.table_format import TableFormat


class FirstMovesTable:
    """B5: the first candidate moves of one run, one row per candidate with its decision and the lengths after it."""

    ROW_COUNT: ClassVar[int] = 10
    FILE_NAME: ClassVar[str] = "first_moves"

    @staticmethod
    def write(result: ExperimentResult) -> None:
        rows = []
        for iteration_log in result.solver_solution.meta_data[
            : FirstMovesTable.ROW_COUNT
        ]:
            rows.append(
                {
                    "Iter.": iteration_log.iteration,
                    "$T$": TableFormat.format_length(iteration_log.temperature),
                    "$(i, j)$": f"({iteration_log.i}, {iteration_log.j})",
                    "$\\Delta$": TableFormat.format_length(iteration_log.delta),
                    "$p$": TableFormat.format_probability(
                        iteration_log.acceptance_probability
                    ),
                    "$u$": TableFormat.format_probability(iteration_log.random_draw),
                    "Decision": "accepted" if iteration_log.accepted else "rejected",
                    "Current": TableFormat.format_length(iteration_log.current.cost),
                    "Best": TableFormat.format_length(iteration_log.best.cost),
                }
            )

        TableFormat.write(FirstMovesTable.FILE_NAME, pd.DataFrame(rows), "rrcrrrlrr")
