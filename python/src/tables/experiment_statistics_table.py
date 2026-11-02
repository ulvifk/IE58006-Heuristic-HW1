import pandas as pd

from data.solution_set import ExperimentResult, SolutionSet
from tables.table_format import TableFormat


class ExperimentStatisticsTable:
    """
    Part C: one row per (T0, alpha) over its seeds:

    - best, mean and sample standard deviation of the final best length
    - candidate moves before T reaches Tmin; the same for every seed, since the
      cooling schedule does not depend on the random moves
    - percentage of proposed worse moves accepted, pooled over the seeds
    """

    @staticmethod
    def write(solution_set: SolutionSet, file_name: str) -> None:
        runs = pd.DataFrame(
            [
                ExperimentStatisticsTable._build_run(result)
                for result in solution_set.results
            ]
        )
        statistics = (
            runs.groupby(["initial_temperature", "cooling_rate"])
            .agg(
                best=("best_cost", "min"),
                mean=("best_cost", "mean"),
                standard_deviation=("best_cost", "std"),
                move_count=("move_count", "first"),
                worse_proposed=("worse_proposed", "sum"),
                worse_accepted=("worse_accepted", "sum"),
            )
            .reset_index()
        )
        worse_accepted_percentage = (
            100 * statistics["worse_accepted"] / statistics["worse_proposed"]
        )

        table = pd.DataFrame(
            {
                "$T_0$": statistics["initial_temperature"].map(
                    TableFormat.format_length
                ),
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
                ): worse_accepted_percentage.map(TableFormat.format_length),
            }
        )
        TableFormat.write(file_name, table)

    @staticmethod
    def _build_run(result: ExperimentResult) -> dict[str, float]:
        """One run's T0 and alpha, final best length, candidate count and worse moves proposed and accepted."""
        iteration_logs = result.solver_solution.meta_data

        worse_proposed = 0
        worse_accepted = 0
        for iteration_log in iteration_logs:
            if iteration_log.acceptance_probability is None:
                continue

            worse_proposed += 1
            if iteration_log.accepted:
                worse_accepted += 1

        return {
            "initial_temperature": result.config.initial_temperature,
            "cooling_rate": result.config.cooling_rate,
            "best_cost": result.solver_solution.solution.cost,
            "move_count": len(iteration_logs),
            "worse_proposed": worse_proposed,
            "worse_accepted": worse_accepted,
        }
