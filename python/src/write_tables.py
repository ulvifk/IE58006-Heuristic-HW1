from data.solution_set_parser import SolutionSetParser
from tables.b5.final_best_tour import FinalBestTour
from tables.b5.first_moves_table import FirstMovesTable
from tables.c1.best_length_statistics_table import BestLengthStatisticsTable

EXPERIMENTS_DIRECTORY = "../experiments"

baseline = SolutionSetParser.from_glob(f"{EXPERIMENTS_DIRECTORY}/baseline/*.json")
FirstMovesTable.write(baseline.results[0])
FinalBestTour.write(baseline.results[0])

cooling_rate = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c1_cooling_rate/*.json"
)
BestLengthStatisticsTable.write(cooling_rate)
