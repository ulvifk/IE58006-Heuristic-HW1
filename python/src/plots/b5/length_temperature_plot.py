from typing import ClassVar

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

from data.solution_set import ExperimentResult
from plots.plot_format import PlotFormat


class LengthTemperaturePlot:
    """
    B5: one run over its iterations, in two panels sharing the iteration axis:

    - top: current and best-so-far tour length
    - bottom: the temperature each candidate was judged at
    """

    FILE_NAME: ClassVar[str] = "length_temperature"

    @staticmethod
    def write(result: ExperimentResult) -> None:
        iteration_logs = result.solver_solution.meta_data

        lengths = []
        for iteration_log in iteration_logs:
            lengths.append(
                {
                    "Iteration": iteration_log.iteration,
                    "Length": iteration_log.current.cost,
                    "Tour": "Current",
                }
            )
            lengths.append(
                {
                    "Iteration": iteration_log.iteration,
                    "Length": iteration_log.best.cost,
                    "Tour": "Best so far",
                }
            )
        temperatures = pd.DataFrame(
            {
                "Iteration": [
                    iteration_log.iteration for iteration_log in iteration_logs
                ],
                "Temperature": [
                    iteration_log.temperature for iteration_log in iteration_logs
                ],
            }
        )

        PlotFormat.apply_theme()
        figure, (length_axes, temperature_axes) = plt.subplots(
            2,
            1,
            sharex=True,
            figsize=(PlotFormat.FIGURE_WIDTH, 4.5),
            height_ratios=(2, 1),
        )
        sns.lineplot(
            data=pd.DataFrame(lengths),
            x="Iteration",
            y="Length",
            hue="Tour",
            ax=length_axes,
        )
        sns.lineplot(
            data=temperatures,
            x="Iteration",
            y="Temperature",
            color=PlotFormat.COLORS[2],
            drawstyle="steps-post",
            ax=temperature_axes,
        )
        length_axes.legend(title=None)
        length_axes.set_title(PlotFormat.build_config_title(result.config))

        PlotFormat.save(LengthTemperaturePlot.FILE_NAME, figure)
