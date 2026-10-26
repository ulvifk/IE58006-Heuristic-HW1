from typing import ClassVar

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

from data.solution_set import ExperimentResult
from plots.plot_format import PlotFormat


class FinalTourPlot:
    """
    B5: the best tour of one run drawn over the city coordinates, closed back to its
    first city; each city labeled with its id and each edge with its length.
    """

    FILE_NAME: ClassVar[str] = "final_best_tour"

    @staticmethod
    def write(result: ExperimentResult) -> None:
        problem_data = result.problem_data
        best = result.solver_solution.solution
        city_ids = best.tour.city_ids
        closed_tour = [
            problem_data.cities[city_id] for city_id in (*city_ids, city_ids[0])
        ]
        cities = pd.DataFrame(
            {
                "x": [city.x for city in problem_data.cities],
                "y": [city.y for city in problem_data.cities],
            }
        )

        PlotFormat.apply_theme()
        figure, axes = plt.subplots(figsize=(PlotFormat.FIGURE_WIDTH, 4.5))
        axes.plot(
            [city.x for city in closed_tour],
            [city.y for city in closed_tour],
            color=PlotFormat.COLORS[0],
            zorder=1,
        )
        sns.scatterplot(
            data=cities,
            x="x",
            y="y",
            color=PlotFormat.COLORS[1],
            s=40,
            zorder=2,
            ax=axes,
        )
        for city in problem_data.cities:
            axes.annotate(
                str(city.id),
                (city.x, city.y),
                textcoords="offset points",
                xytext=(5, 5),
                fontsize=8,
            )
        for from_city, to_city in zip(closed_tour, closed_tour[1:], strict=False):
            axes.annotate(
                f"{problem_data.distance_matrix.distances[from_city.id][to_city.id]:.2f}",
                ((from_city.x + to_city.x) / 2, (from_city.y + to_city.y) / 2),
                ha="center",
                va="center",
                fontsize=6,
                color=PlotFormat.COLORS[0],
                bbox={"facecolor": "white", "edgecolor": "none", "pad": 0.5},
            )
        axes.set_aspect("equal")
        axes.set_title(
            f"Final best tour, length {best.cost:.2f}\n"
            + PlotFormat.build_config_title(result.config)
        )

        PlotFormat.save(FinalTourPlot.FILE_NAME, figure)
