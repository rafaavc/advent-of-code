from utils import solve_problems
from typing import Union
import numpy as np


class Monkey:
    id: int
    items = []
    expression: str
    test_div: int
    if_true: int
    if_false: int
    n_inspected: int = 0

    def __init__(self, id_):
        self.id = id_

    def __str__(self):
        return f"{self.id}, {self.items}, {self.expression}, {self.test_div}, {self.if_true}, {self.if_false})"


def parse_data(data):
    monkeys = {}
    current_monkey: Union[Monkey, None] = None
    for line in data:
        if line[:6] == "Monkey":
            current_monkey = Monkey(int(line[7:-1]))
        elif line[:16] == "  Starting items":
            current_monkey.items = [int(x) for x in line[18:].split(", ")]
        elif line[:11] == "  Operation":
            current_monkey.expression = line[19:]
        elif line[:6] == "  Test":
            current_monkey.test_div = int(line[21:])
        elif line[:11] == "    If true":
            current_monkey.if_true = int(line[29:])
        elif line[:12] == "    If false":
            current_monkey.if_false = int(line[30:])
            monkeys[current_monkey.id] = current_monkey
    return monkeys


def solve(data, divide_by_3=True, n_rounds=20):
    monkeys = parse_data(data)
    lcm = np.lcm.reduce([m.test_div for m in monkeys.values()])
    for cycle in range(n_rounds):
        for monkey in monkeys.values():
            while len(monkey.items) > 0 and (old := monkey.items.pop(0)) is not None:
                new = eval(monkey.expression)
                if divide_by_3:
                    new //= 3
                else:
                    new %= lcm  # we only care about whether the result of the divisibility check (for all checks)
                new_monkey = monkey.if_true if new % monkey.test_div == 0 else monkey.if_false
                monkeys[new_monkey].items.append(new)
                monkey.n_inspected += 1
    monkeys_list = list(monkeys.values())
    monkeys_list.sort(key=lambda m: m.n_inspected, reverse=True)
    return monkeys_list[0].n_inspected * monkeys_list[1].n_inspected


def solution1(data):
    return solve(data)


def solution2(data):
    return solve(data, divide_by_3=False, n_rounds=10000)


solve_problems(11, solution1, solution2)
