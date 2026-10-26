from typing import ClassVar

import pandas as pd

from data.solution_set import ExperimentResult, SolutionSet
from tables.table_format import TableFormat


class CoolingRateTable:
    """
    C1: one row per cooling factor alpha over its seeds:

    - (a) best, mean and sample standard deviation of the final best length
    - (b) candidate moves before T reaches Tmin; the same for every seed, since the
      cooling schedule does not depend on the random moves
    - (c) percentage of proposed worsening moves accepted, pooled over the seeds
    """

    FILE_NAME: ClassVar[str] = "c1_cooling_rate"

    @staticmethod
    def write(solution_set: SolutionSet) -> None:
        runs = pd.DataFrame(
            [CoolingRateTable._build_run(result) for result in solution_set.results]
        )
        statistics = (
            runs.groupby("cooling_rate")
            .agg(
                best=("best_cost", "min"),
                mean=("best_cost", "mean"),
                standard_deviation=("best_cost", "std"),
                move_count=("move_count", "first"),
                worsening_proposed=("worsening_proposed", "sum"),
                worsening_accepted=("worsening_accepted", "sum"),
            )
            .reset_index()
        )
        worsening_accepted_percentage = (
            100 * statistics["worsening_accepted"] / statistics["worsening_proposed"]
        )

        table = pd.DataFrame(
            {
                "$\\alpha$": statistics["cooling_rate"].map(TableFormat.format_length),
                "Best": statistics["best"].map(TableFormat.format_length),
                "Mean": statistics["mean"].map(TableFormat.format_length),
                "Std. dev.": statistics["standard_deviation"].map(
                    TableFormat.format_length
                ),
                TableFormat.stack_header("Candidate", "moves"): statistics[
                    "move_count"
                ],
                TableFormat.stack_header(
                    "Worse moves", "accepted (\\%)"
                ): worsening_accepted_percentage.map(TableFormat.format_length),
            }
        )
        TableFormat.write(CoolingRateTable.FILE_NAME, table)

    @staticmethod
    def _build_run(result: ExperimentResult) -> dict[str, float]:
        """One run's alpha, final best length, candidate count and worsening moves proposed and accepted."""
        iteration_logs = result.solver_solution.meta_data

        worsening_proposed = 0
        worsening_accepted = 0
        for iteration_log in iteration_logs:
            if iteration_log.acceptance_probability is None:
                continue

            worsening_proposed += 1
            if iteration_log.accepted:
                worsening_accepted += 1

        return {
            "cooling_rate": result.config.cooling_rate,
            "best_cost": result.solver_solution.solution.cost,
            "move_count": len(iteration_logs),
            "worsening_proposed": worsening_proposed,
            "worsening_accepted": worsening_accepted,
        }
