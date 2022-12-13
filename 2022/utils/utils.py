from typing import Callable, List, AnyStr, NewType
from os.path import exists


def read_data(day: int, part: int):
    file = f"./data/day{day}"
    if not exists(file):
        file += f"-{part}"

    with open(file, "r") as f:
        return [line.rstrip("\n") for line in f.readlines()]


Solver = NewType("Solver", Callable[[List[AnyStr]], int])


def solve_problems(day: int, solution1: Solver, solution2: Solver = None):
    problem1_solution = -1
    if solution1 is not None:
        print("Solving problem1...")
        problem1_solution = solution1(read_data(day, 1))
        print(f"Problem1 solution = {problem1_solution}")

    if solution2 is not None:
        print("\n------------------\n")
        print("Solving problem2...")
        problem2_solution = solution2(read_data(day, 2))
        print(f"Problem1 solution = {problem1_solution}\nProblem2 solution = {problem2_solution}")
