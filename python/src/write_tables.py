from data.solution_set_parser import SolutionSetParser
from tables.first_moves_table import FirstMovesTable

EXPERIMENTS_DIRECTORY = "../experiments"

baseline = SolutionSetParser.from_glob(f"{EXPERIMENTS_DIRECTORY}/baseline/*.json")
FirstMovesTable.write(baseline.results[0])
