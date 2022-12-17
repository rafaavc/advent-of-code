from utils import solve_problems, merge_segments, add_segment_by_merging
from typing import List, Union
import numpy as np


def manhattan(v1: np.array, v2: np.array):
    return np.sum(np.abs(v1 - v2))


def parse_data(data):
    parsed_data = []
    for line in data:
        parsed_data.append([[int(v.split("=")[1]) for v in coords.split(", ")] for coords in line.split(":")])
    return parsed_data


def get_no_beacon_positions(parsed_data: List[Union[np.array, int]], row: int):
    segments = []
    for d in parsed_data:
        sensor = np.array(d[0])
        beacon = np.array(d[1])
        distance = manhattan(sensor, beacon)

        d2 = distance - abs(row - sensor[1])
        if d2 < 0:
            continue
        segments.append([sensor[0] - d2, sensor[0] + d2])

    segments = merge_segments(segments)
    return sum([segment[1] - segment[0] for segment in segments])


def solution1(data: List[str]):
    row = int(data[0])
    parsed_data = parse_data(data[2:])
    return get_no_beacon_positions(parsed_data, row)


def calculate_freq(x, y):
    return x * 4000000 + y


def solution2(data):
    max_coord = int(data[1])
    parsed_data = parse_data(data[2:])
    rows = [[] for _ in range(max_coord+1)]
    for j, (sensor, beacon) in enumerate(parsed_data):
        print(f"Sensor {j+1}/{len(parsed_data)}")
        distance = manhattan(np.array(sensor), np.array(beacon))
        x, y = sensor
        for i in range(distance + 1):
            d = distance - i
            for y_ in [y - i, y + i]:
                if 0 <= y_ <= max_coord:
                    add_segment_by_merging(rows[y_], [x - d, x + d])

    result = []
    for i, row in enumerate(rows):
        if len(row) != 1:  # ignoring case where the beacon is at an edge
            result.append(calculate_freq(row[0][1] + 1, i))

    return result[0] if len(result) == 1 else f"ERROR, result = {result}"


solve_problems(15, solution1, solution2)
