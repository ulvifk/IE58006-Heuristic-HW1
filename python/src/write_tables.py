from data.solution_set import SolutionSet
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
ExperimentStatisticsTable.write(cooling_rate, "c1_cooling_rate")

initial_temperature = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c2_initial_temperature/*.json"
)
ExperimentStatisticsTable.write(initial_temperature, "c2_initial_temperature")

# the recommended configuration next to the baseline settings over the same seeds
baseline_settings = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c1_cooling_rate/t0-3.00_alpha-0.90_*.json"
)
recommended = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c3_recommended/*.json"
)
ExperimentStatisticsTable.write(
    SolutionSet(results=baseline_settings.results + recommended.results),
    "c3_recommended",
)
