from data.solution_set_parser import SolutionSetParser
from tables.b5.final_best_tour import FinalBestTour
from tables.b5.first_moves_table import FirstMovesTable

EXPERIMENTS_DIRECTORY = "../experiments"

baseline = SolutionSetParser.from_glob(f"{EXPERIMENTS_DIRECTORY}/baseline/*.json")
FirstMovesTable.write(baseline.results[0])
FinalBestTour.write(baseline.results[0])
