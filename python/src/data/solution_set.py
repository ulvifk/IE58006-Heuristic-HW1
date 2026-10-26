from dataclasses import dataclass


@dataclass(frozen=True, slots=True)
class SimulatedAnnealingConfig:
    initial_temperature: float  # T0
    minimum_temperature: float  # Tmin; the search stops once T is at or below it
    cooling_rate: float  # alpha in T <- alpha * T
    moves_per_temperature: int  # candidate moves between two cooling steps

    seed: int
    config_key: str  # e.g. t0-3.00_alpha-0.90_seed-7


@dataclass(frozen=True, slots=True)
class Tour:
    city_ids: tuple[
        int, ...
    ]  # [position] -> city id; the return to the first city is implied


@dataclass(frozen=True, slots=True)
class Solution:
    tour: Tour
    cost: float  # closed-tour length, including the return to the first city


@dataclass(frozen=True, slots=True)
class IterationLog:
    iteration: int  # 1 for the first candidate
    temperature: float  # T the candidate was judged at

    i: int  # the 2-opt move's positions
    j: int
    delta: float  # tour length change if accepted; negative improves
    acceptance_probability: float | None  # p = e^(-delta / T); None unless worsening
    random_draw: float | None  # u from U(0, 1); None unless worsening
    accepted: bool

    current: Solution  # after the decision
    best: Solution


@dataclass(frozen=True, slots=True)
class SolverSolution:
    solution: Solution  # the best solution of the run
    meta_data: tuple[
        IterationLog, ...
    ]  # [iteration - 1] -> what happened to that candidate


@dataclass(frozen=True, slots=True)
class ExperimentResult:
    config: SimulatedAnnealingConfig
    solver_solution: SolverSolution


@dataclass(frozen=True, slots=True)
class SolutionSet:
    results: tuple[ExperimentResult, ...]  # one per run file, ordered by file path
