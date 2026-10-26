from data.solution_set_parser import SolutionSetParser
from tables.b5.final_best_tour import FinalBestTour
from tables.b5.first_moves_table import FirstMovesTable
from tables.experiment_statistics_table import ExperimentStatisticsTable

EXPERIMENTS_DIRECTORY = "../experiments"

baseline = SolutionSetParser.from_glob(f"{EXPERIMENTS_DIRECTORY}/baseline/*.json")
FirstMovesTable.write(baseline.results[0])
FinalBestTour.write(baseline.results[0])

cooling_rate = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c1_cooling_rate/*.json"
)
ExperimentStatisticsTable.write(
    cooling_rate, lambda config: config.cooling_rate, "$\\alpha$", "c1_cooling_rate"
)

initial_temperature = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c2_initial_temperature/*.json"
)
ExperimentStatisticsTable.write(
    initial_temperature,
    lambda config: config.initial_temperature,
    "$T_0$",
    "c2_initial_temperature",
)
