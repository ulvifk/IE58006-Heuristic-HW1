# IE 58006 Heuristic Optimization, Assignment 1: Simulated Annealing

Simulated annealing with random 2-opt moves for a 20-city Euclidean TSP, the
experiments the assignment asks for, and the report built from their results.

The simulated annealing and the experiments are implemented in Java, under
`src/main/java`. Python, under `python/`, is used only for analysis and plotting: it
reads the results Java writes and turns them into the report's tables and figures.

The project has three parts that run in order:

1. Java solves every run and writes one JSON file per run to `experiments/`.
2. Python reads those files and writes the report's tables and figures into `latex/`.
3. LaTeX builds the report from `latex/main.tex`.

## Requirements

- JDK 27. Gradle downloads it through the toolchain resolver if it is not installed.
- [uv](https://docs.astral.sh/uv/) for the Python analysis (Python 3.14).
- A TeX distribution with `latexmk` and `pdflatex`.

## Running

```sh
# 1. Run the baseline and every Part C experiment; writes experiments/<experiment>/*.json
./gradlew run

# 2. Write the tables and figures into latex/tables and latex/figures
cd python
uv run python src/write_tables.py
uv run python src/write_plots.py

# 3. Build the report into latex/build/main.pdf
cd ../latex
latexmk main.tex
```

Runs are reproducible: each configuration carries its own seed, and the baseline run
uses seed 7.

## Algorithm

- A tour is a permutation of the 20 cities, closed back to its first city. Distances
  are Euclidean, rounded to two decimals.
- Each iteration draws one random 2-opt move on the current tour and evaluates it from
  the four edges it changes:
  $\Delta = d(t_i, t_j) + d(t_{i+1}, t_{j+1}) - d(t_i, t_{i+1}) - d(t_j, t_{j+1})$.
  An accepted move reverses positions $i+1, \dots, j$.
- An improving move ($\Delta < 0$) is accepted outright. A worse move ($\Delta > 0$) is
  accepted when $u < e^{-\Delta / T}$, with $u \sim U(0, 1)$. A move with $\Delta = 0$
  is rejected.
- After every 20 candidate moves the temperature cools as $T \leftarrow \alpha T$, and
  the run stops once $T \le T_{min}$.
- The best-so-far tour is kept separately and only replaced by a strictly better one.

## Development

```sh
./gradlew formatAndCheck            # format Java, compile, test and run SpotBugs
cd python && uv run ruff format . && uv run ruff check . && uv run pyrefly check
```
