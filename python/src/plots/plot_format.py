from pathlib import Path
from typing import ClassVar

import matplotlib.pyplot as plt
import seaborn as sns
from matplotlib.figure import Figure

from data.solution_set import SimulatedAnnealingConfig


class PlotFormat:
    """
    The theme and output location every plot shares, so all figures in the report look
    alike; a light background with the series colors of the dark palette from the
    security-task-allocation overview.
    """

    COLORS: ClassVar[tuple[str, ...]] = ("#3987e5", "#d95926", "#199e70", "#c98500")

    FIGURE_WIDTH: ClassVar[float] = 6.5  # inches, the text width of a fullpage page
    DPI: ClassVar[int] = 300

    # main.tex includes every figure from latex/figures
    LATEX_FIGURES_DIRECTORY: ClassVar[Path] = (
        Path(__file__).parents[3] / "latex" / "figures"
    )

    @staticmethod
    def apply_theme() -> None:
        sns.set_theme(
            style="whitegrid", context="paper", palette=list(PlotFormat.COLORS)
        )

    @staticmethod
    def build_config_title(config: SimulatedAnnealingConfig) -> str:
        """The run's parameters as a plot title line; e.g. "T0 = 3.00, alpha = 0.90, Tmin = 0.02, cooling every 20 candidate moves, seed 7"."""
        return (
            f"$T_0$ = {config.initial_temperature:.2f}, "
            f"$\\alpha$ = {config.cooling_rate:.2f}, "
            f"$T_{{min}}$ = {config.minimum_temperature:.2f}, "
            f"cooling every {config.moves_per_temperature} candidate moves, "
            f"seed {config.seed}"
        )

    @staticmethod
    def save(file_name: str, figure: Figure) -> None:
        """Writes latex/figures/<file_name>.png, for main.tex to include, and closes the figure."""
        PlotFormat.LATEX_FIGURES_DIRECTORY.mkdir(parents=True, exist_ok=True)
        figure.savefig(
            PlotFormat.LATEX_FIGURES_DIRECTORY / f"{file_name}.png",
            dpi=PlotFormat.DPI,
            bbox_inches="tight",
        )
        plt.close(figure)
