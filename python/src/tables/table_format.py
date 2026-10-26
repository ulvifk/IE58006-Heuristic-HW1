from pathlib import Path
from typing import ClassVar

import pandas as pd


class TableFormat:
    """The number formatting and LaTeX layout every table shares, so all tables in the report look alike."""

    LENGTH_DECIMALS: ClassVar[int] = 2  # tour lengths, deltas, temperatures
    PROBABILITY_DECIMALS: ClassVar[int] = 4  # p and u
    MISSING: ClassVar[str] = "--"  # p and u of a move that is not worsening

    # main.tex inputs every table from latex/tables
    LATEX_TABLES_DIRECTORY: ClassVar[Path] = (
        Path(__file__).parents[3] / "latex" / "tables"
    )

    @staticmethod
    def format_length(value: float) -> str:
        return f"{value:.{TableFormat.LENGTH_DECIMALS}f}"

    @staticmethod
    def format_probability(value: float | None) -> str:
        if value is None:
            return TableFormat.MISSING

        return f"{value:.{TableFormat.PROBABILITY_DECIMALS}f}"

    @staticmethod
    def write(file_name: str, table: pd.DataFrame, column_format: str) -> None:
        """
        Writes an already formatted table to latex/tables/<file_name>.tex as a booktabs
        tabular, its column names as the header, for main.tex to input.
        """
        latex = table.to_latex(index=False, escape=False, column_format=column_format)

        TableFormat.LATEX_TABLES_DIRECTORY.mkdir(parents=True, exist_ok=True)
        (TableFormat.LATEX_TABLES_DIRECTORY / f"{file_name}.tex").write_text(latex)
