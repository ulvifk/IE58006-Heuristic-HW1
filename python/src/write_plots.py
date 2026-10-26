from data.solution_set_parser import SolutionSetParser
from plots.length_temperature_plot import LengthTemperaturePlot

EXPERIMENTS_DIRECTORY = "../experiments"

baseline = SolutionSetParser.from_glob(f"{EXPERIMENTS_DIRECTORY}/baseline/*.json")
LengthTemperaturePlot.write(baseline.results[0])
