from utils import solve_problems
import re

N_STACKS = 9  # lazy


def get_position(stack: int):
    return 1 + stack * 4


def solve(data, move_strategy):
    expr = re.compile(r"move (.*) from (.*) to (.*)")
    stacks = [[] for _ in range(N_STACKS)]
    gathering_state = True
    for line in data:
        if len(line) == 0:
            continue
        if gathering_state and "[" not in line:
            gathering_state = False
            continue

        if gathering_state:
            for stack in range(len(stacks)):
                position = get_position(stack)
                if position < len(line) and line[position] != " ":
                    stacks[stack].insert(0, line[get_position(stack)])
        else:
            match = re.match(expr, line)
            [amount, from_stack, to_stack] = [int(x) for x in match.groups()]
            move_strategy(amount, stacks[from_stack - 1], stacks[to_stack - 1])

    return ''.join([s.pop() for s in stacks])


def move1(amount, from_stack, to_stack):
    for _ in range(amount):
        to_stack.append(from_stack.pop())


def move2(amount, from_stack, to_stack):
    to_stack += from_stack[-amount:]
    del from_stack[-amount:]


def problem1(data):
    return solve(data, move1)


def problem2(data):
    return solve(data, move2)


solve_problems(5, problem1, problem2)
