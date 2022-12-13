from utils import solve_problems
import numpy as np

deltas = {
    "R": np.array((1, 0)),
    "U": np.array((0, 1)),
    "L": np.array((-1, 0)),
    "D": np.array((0, -1))
}


def solve(data, n_knots):
    knots = [np.array((0., 0.)) for _ in range(n_knots)]
    last_knot_positions = {tuple(knots[-1])}
    for line in data:
        command = line[0]
        amount = int(line[2:])
        for _ in range(amount):
            knots[0] += deltas[command]
            h = knots[0]
            for i in range(1, len(knots)):
                d = h - knots[i]
                if 2 in np.abs(d):
                    d = np.sign(d) * np.ceil(np.abs(d) / 2.)
                    knots[i] += d
                h = knots[i]
            last_knot_positions.add(tuple(knots[-1]))
    return len(last_knot_positions)


def solution1(data):
    return solve(data, 2)


def solution2(data):
    return solve(data, 10)


solve_problems(9, solution1, solution2)
