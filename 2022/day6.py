from utils import solve_problems


def detect_packet(lines, length):
    [data] = lines
    for i in range(length, len(data)+1):
        if len(set(data[i-length:i])) == length:
            return i


def problem1(lines):
    return detect_packet(lines, 4)


def problem2(lines):
    return detect_packet(lines, 14)


solve_problems(6, problem1, problem2)
