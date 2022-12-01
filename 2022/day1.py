from utils import solve_problems
from queue import PriorityQueue


def fill_elfs_pq(data):
    elfs = PriorityQueue()
    current_sum = 0
    for line in data:
        if line != "":
            current_sum += int(line)
        else:
            elfs.put(-current_sum)
            current_sum = 0
    return elfs


def problem1(data):
    elfs = fill_elfs_pq(data)
    return abs(elfs.get())


def problem2(data):
    elfs = fill_elfs_pq(data)
    return sum([abs(elfs.get()) for _ in range(3)])


solve_problems(1, problem1, problem2)
