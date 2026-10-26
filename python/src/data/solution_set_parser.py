import glob
import json
from typing import Any

from data.solution_set import (
    ExperimentResult,
    IterationLog,
    SimulatedAnnealingConfig,
    Solution,
    SolutionSet,
    SolverSolution,
    Tour,
)


class SolutionSetParser:
    @staticmethod
    def from_glob(pattern: str) -> SolutionSet:
        """
        Reads every run file matching a glob pattern, ordered by path; e.g.
        "../experiments/c1_cooling_rate/*.json" or "../experiments/*/t0-3.00_*.json".
        """
        results = []
        for path in sorted(glob.glob(pattern)):
            with open(path) as file:
                results.append(
                    SolutionSetParser._parse_experiment_result(json.load(file))
                )

        return SolutionSet(results=tuple(results))

    @staticmethod
    def _parse_experiment_result(raw: dict[str, Any]) -> ExperimentResult:
        raw_solver_solution = raw["solver_solution"]
        iteration_logs = tuple(
            SolutionSetParser._parse_iteration_log(raw_log)
            for raw_log in raw_solver_solution["meta_data"]
        )

        return ExperimentResult(
            config=SimulatedAnnealingConfig(**raw["config"]),
            solver_solution=SolverSolution(
                solution=SolutionSetParser._parse_solution(
                    raw_solver_solution["solution"]
                ),
                meta_data=iteration_logs,
            ),
        )

    @staticmethod
    def _parse_iteration_log(raw: dict[str, Any]) -> IterationLog:
        return IterationLog(
            iteration=raw["iteration"],
            temperature=raw["temperature"],
            i=raw["i"],
            j=raw["j"],
            delta=raw["delta"],
            acceptance_probability=raw["acceptance_probability"],
            random_draw=raw["random_draw"],
            accepted=raw["accepted"],
            current=SolutionSetParser._parse_solution(raw["current"]),
            best=SolutionSetParser._parse_solution(raw["best"]),
        )

    @staticmethod
    def _parse_solution(raw: dict[str, Any]) -> Solution:
        return Solution(
            tour=Tour(city_ids=tuple(raw["tour"]["city_ids"])),
            cost=raw["cost"],
        )
