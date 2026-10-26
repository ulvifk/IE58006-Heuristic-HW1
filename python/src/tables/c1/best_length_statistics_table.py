from typing import ClassVar

import pandas as pd

from data.solution_set import SolutionSet
from tables.table_format import TableFormat


class BestLengthStatisticsTable:
    """C1(a): per cooling factor alpha, the best, mean and sample standard deviation of the final best length over the seeds."""

    FILE_NAME: ClassVar[str] = "c1_best_length_statistics"

    @staticmethod
    def write(solution_set: SolutionSet) -> None:
        runs = pd.DataFrame(
            {
                "cooling_rate": [
                    result.config.cooling_rate for result in solution_set.results
                ],
                "best_cost": [
                    result.solver_solution.solution.cost
                    for result in solution_set.results
                ],
            }
        )
        statistics = (
            runs.groupby("cooling_rate")["best_cost"]
            .agg(["min", "mean", "std"])
            .reset_index()
        )

        table = pd.DataFrame(
            {
                "$\\alpha$": statistics["cooling_rate"].map(TableFormat.format_length),
                "Best": statistics["min"].map(TableFormat.format_length),
                "Mean": statistics["mean"].map(TableFormat.format_length),
                "Std. dev.": statistics["std"].map(TableFormat.format_length),
            }
        )
        TableFormat.write(BestLengthStatisticsTable.FILE_NAME, table, "rrrr")
