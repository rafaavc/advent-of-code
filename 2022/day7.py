from utils import solve_problems
from typing import List

COMMAND_ID = "$"
DIR_ID = "dir"
CD_COMMAND = "cd"
PARENT_DIR = ".."


def dfs(data: List[str], current_line: int, total, add_to_total):
    dir_size = 0
    while current_line < len(data) and data[current_line][0] != COMMAND_ID:
        if data[current_line][:3] != DIR_ID:
            dir_size += int(data[current_line].split(" ")[0])
        current_line += 1

    while current_line < len(data):
        if data[current_line][2:4] == CD_COMMAND:
            if data[current_line].split(" ")[2] != PARENT_DIR:
                size, current_line = dfs(data, current_line + 2, total, add_to_total)
                dir_size += size
            else:
                current_line += 1
                break

    add_to_total(dir_size, total)
    return dir_size, current_line


def problem1(data: List[str]):
    def add_to_total(dir_size, total):
        if dir_size <= 100_000:
            total.value += dir_size

    class Result:
        value = 0
    result = Result()
    dfs(data, 2, result, add_to_total)
    return result.value


def problem2(data: List[str]):
    def add_to_total(dir_size, total):
        total.append(dir_size)

    dir_sizes = []
    total_size, _ = dfs(data, 2, dir_sizes, add_to_total)

    unused_space = 70_000_000 - total_size
    need_to_free = 30_000_000 - unused_space
    if need_to_free <= 0:
        return 0

    dir_sizes.sort()
    for size in dir_sizes:
        if size >= need_to_free:
            return size

    return -1


solve_problems(7, problem1, problem2)
