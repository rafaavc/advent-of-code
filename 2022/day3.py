from utils import solve_problems


def get_priority(c):
    t = ord(c) - 96
    return t if t > 0 else ord(c) - 65 + 27


def problem1(data):
    result = 0
    for line in data:
        mid = int(len(line) / 2)
        result += get_priority((set(line[:mid]) & set(line[mid:])).pop())
    return result


def problem2(data):
    result = 0
    group = []
    for line in data:
        group.append(line)
        if len(group) < 3:
            continue

        result += get_priority(set.intersection(*[set(x) for x in group]).pop())
        group.clear()
    return result


solve_problems(3, problem1, problem2)
