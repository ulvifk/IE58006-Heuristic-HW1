from data.solution_set_parser import SolutionSetParser
from tables.b5.final_best_tour import FinalBestTour
from tables.b5.first_moves_table import FirstMovesTable
from tables.c1.cooling_rate_table import CoolingRateTable

EXPERIMENTS_DIRECTORY = "../experiments"

baseline = SolutionSetParser.from_glob(f"{EXPERIMENTS_DIRECTORY}/baseline/*.json")
FirstMovesTable.write(baseline.results[0])
FinalBestTour.write(baseline.results[0])

cooling_rate = SolutionSetParser.from_glob(
    f"{EXPERIMENTS_DIRECTORY}/c1_cooling_rate/*.json"
)
CoolingRateTable.write(cooling_rate)
