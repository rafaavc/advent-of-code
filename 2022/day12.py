from utils import solve_problems
from utils import dijkstra
import numpy as np


def parse_data(data):
    start = None
    end = None
    grid = []
    for j, line in enumerate(data):
        grid.append([])
        for i, c in enumerate(line):
            if c == 'S':
                start = (j, i)
                grid[-1].append(-1)
            elif c == 'E':
                end = (j, i)
                grid[-1].append(-1)
            else:
                grid[-1].append(ord(c) - ord('a'))
    grid[start[0]][start[1]] = 0
    grid[end[0]][end[1]] = ord('z') - ord('a')
    return grid, start, end


deltas = [
    np.array((1, 0)),
    np.array((0, 1)),
    np.array((-1, 0)),
    np.array((0, -1)),
]

def dijkstra_day12(*data):
    def expand(grid, current, current_steps, pq, seen):
        j, i = current
        for delta in deltas:
            new_current = current + delta
            new_j, new_i = new_current

            if new_j < 0 or new_j >= len(grid) or new_i < 0 or new_i >= len(grid[0]) \
                    or grid[new_j][new_i] > grid[j][i] + 1 or grid[new_j][new_i] == -1:
                continue

            new_current = tuple(new_current)
            if new_current not in seen:
                pq.put((current_steps + 1, new_current))
                seen.add(new_current)


    def check_end(node, end):
        node = np.array(node)
        return np.sum(node == end) == 2
    grid, start, end = data
    return dijkstra(grid, expand, check_end, start, end)


def solution1(data):
    return dijkstra_day12(*parse_data(data))


def solution2(data):
    grid, _, end = parse_data(data)
    result = 1000000

    for j, line in enumerate(grid):
        for i, height in enumerate(line):
            if height == 0:
                if (v := dijkstra_day12(grid, (j, i), end)) > 0:
                    result = min(result, v)

    return result


solve_problems(12, solution1, solution2)
