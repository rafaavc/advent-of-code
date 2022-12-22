from typing import Callable, List, AnyStr, NewType
from os.path import exists
from time import time as get_time


def read_data(day: int, part: int):
    file = f"./data/day{day}"
    if not exists(file):
        file += f"-{part}"

    with open(file, "r") as f:
        return [line.rstrip("\n") for line in f.readlines()]


Solver = NewType("Solver", Callable[[List[AnyStr]], int])


def solve_problems(day: int, solution1: Solver, solution2: Solver = None):
    if solution1 is not None:
        print("Solving problem1...")
        t = get_time()
        problem1_solution = solution1(read_data(day, 1))
        print(f"Problem1 solution ({round(get_time() - t, 2)}s) = {problem1_solution}")

    if solution2 is not None:
        print("------------------")
        print("Solving problem2...")
        t = get_time()
        problem2_solution = solution2(read_data(day, 2))
        print(f"Problem2 solution ({round(get_time() - t, 2)}s) = {problem2_solution}")
