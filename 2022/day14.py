from utils import solve_problems
from typing import List
import numpy as np


def parse_map(data):
    paths = []
    min_x = 10000
    max_x = -1
    max_y = -1
    for path in data:
        paths.append([])
        for line in path.split(" -> "):
            x, y = [int(x) for x in line.split(",")]
            paths[-1].append(np.array((x, y)))
            min_x = min(min_x, x)
            max_x = max(max_x, x)
            max_y = max(max_y, y)

    cave_map = [["." for _ in range(max_x - min_x + 1)] for _ in range(max_y + 1)]
    for path in paths:
        for p1, p2 in zip(path[:-1], path[1:]):
            delta = p2 - p1
            delta = (delta/abs(np.where(delta == 0, 1, delta))).astype(int)

            while (p1 - delta != p2).any():
                x1, y1 = p1
                cave_map[y1][x1-min_x] = "#"
                p1 += delta
    return cave_map, min_x, max_x, max_y


def solution1(data):
    cave_map, min_x, max_x, max_y = parse_map(data)
    count = 0
    out_of_bounds = False
    while not out_of_bounds:
        current = np.array((500, 1))
        delta = np.array((0, 1))
        while not out_of_bounds:
            while cave_map[current[1]][current[0] - min_x] == ".":
                current += delta
                if current[1] > max_y:
                    out_of_bounds = True
                    break
            next_r = current + np.array((1, 0))
            x_r, y_r = next_r
            next_l = current + np.array((-1, 0))
            x_l, y_l = next_l
            if x_l < min_x:
                out_of_bounds = True
            elif cave_map[y_l][x_l - min_x] == ".":
                current = next_l
            elif x_r > max_x:
                out_of_bounds = True
            elif cave_map[y_r][x_r - min_x] == ".":
                current = next_r
            else:
                current -= delta
                cave_map[current[1]][current[0] - min_x] = "o"
                count += 1
                break

    return count


def parse_map2(data):
    cave_map, min_x, max_x, max_y = parse_map(data)
    cave_map.append(["." for _ in range(len(cave_map[0]))])
    cave_map.append(["#" for _ in range(len(cave_map[0]))])
    max_y += 2
    return cave_map, min_x, max_x, max_y


def expand_map(cave_map: List[List[str]], min_x, max_x, x):
    if x - min_x <= 1:
        for j, row in enumerate(cave_map):
            row.insert(0, "#" if j == len(cave_map) - 1 else ".")
        min_x -= 1
    elif max_x - x <= 1:
        for j, row in enumerate(cave_map):
            row.append("#" if j == len(cave_map) - 1 else ".")
        max_x += 1

    return min_x, max_x


def solution2(data):
    cave_map, min_x, max_x, max_y = parse_map2(data)
    count = 0
    while True:
        current = np.array((500, 1))
        delta = np.array((0, 1))
        while True:
            while cave_map[current[1]][current[0] - min_x] == ".":
                current += delta

            next_r = current + np.array((1, 0))
            next_l = current + np.array((-1, 0))
            x_r, y_r = next_r
            x_l, y_l = next_l

            min_x, max_x = expand_map(cave_map, min_x, max_x, x_r)
            min_x, max_x = expand_map(cave_map, min_x, max_x, x_l)

            if cave_map[y_l][x_l - min_x] == ".":
                current = next_l
            elif cave_map[y_r][x_r - min_x] == ".":
                current = next_r
            else:
                current -= delta
                count += 1
                if current[1] == 0 and current[0] == 500:
                    return count
                cave_map[current[1]][current[0] - min_x] = "o"
                break


solve_problems(14, solution1, solution2)
