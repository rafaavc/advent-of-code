from utils import solve_problems, memoized
from utils import dijkstra
from typing import Set, List


def parse_data(data):
    valves = dict()
    for line in data:
        valve = line[6:8]
        flow_rate, tunnels = line.split("; ")
        flow_rate = int(flow_rate.split("=")[1])
        tunnels = [tunnel.strip(" ") for tunnel in tunnels[22:].split(", ")]
        valves[valve] = (flow_rate, tunnels)
    return valves


@memoized
def shortest_path(start, end, valves):
    def expand(_valves, current_node, current_steps, pq, seen):
        _, tunnels = _valves[current_node]
        for next_node in tunnels:
            if next_node not in seen:
                pq.put((current_steps + 1, next_node))
                seen.add(next_node)

    def check_end(node, _end):
        return node == _end

    return dijkstra(valves, expand, check_end, start, end)


def dfs(valves_to_visit: List[str], _valves, current_valve: str, time_left: int, open_valves: Set[str], current_pressure: int):
    intermediate_result = 0
    if _valves[current_valve][0] > 0:
        time_left -= 1
        intermediate_result += current_pressure
        current_pressure += _valves[current_valve][0]

    open_valves.add(current_valve)
    paths = []

    max_child_result = 0
    for next_valve in valves_to_visit:
        if next_valve == current_valve or next_valve in open_valves:
            continue
        distance = shortest_path(current_valve, next_valve, _valves)
        if distance >= time_left:
            continue
        child_result, child_paths = dfs(valves_to_visit, _valves, next_valve, time_left - distance, open_valves, current_pressure)
        max_child_result = max(child_result + distance * current_pressure, max_child_result)

        for i, (path, child_path_result) in enumerate(child_paths):  # add each of the children's paths
            path.insert(0, current_valve)
            paths.append([path, child_path_result + distance * current_pressure + intermediate_result])

    open_valves.remove(current_valve)

    # add the path that ends at this valve
    paths.append([[current_valve], time_left * current_pressure + intermediate_result])

    max_child_result = max(time_left * current_pressure, max_child_result)
    intermediate_result += max_child_result
    return intermediate_result, paths


def solution1(data):
    valves = parse_data(data)
    valves_to_visit = list(filter(lambda v: valves[v][0] > 0, valves.keys()))
    return dfs(valves_to_visit, valves, "AA", 30, set(), 0)[0]


def solution2(data):
    valves = parse_data(data)
    valves_to_visit = list(filter(lambda v: valves[v][0] > 0, valves.keys()))

    _, paths = dfs(valves_to_visit, valves, "AA", 26, set(), 0)

    path_keys = dict()
    for (path, result) in paths:
        key = "".join(sorted(path))
        if key not in path_keys or result > path_keys[key][0]:
            path_keys[key] = (result, set(path))

    result = 0

    valves_set = set(valves_to_visit)
    for (result1, set1) in path_keys.values():
        complement_set = valves_set - set1
        complement_set.add("AA")
        for (result2, set2) in path_keys.values():
            if set2.issubset(complement_set):
                result = max(result1 + result2, result)

    return result


solve_problems(16, solution1, solution2)
