from utils import solve_problems
from functools import cmp_to_key


def compare_lists(left, right):
    for i in range(max(len(left), len(right))):
        if i >= len(left):
            return 1
        elif i >= len(right):  # out of order
            return -1
        l_value, r_value = left[i], right[i]
        if type(l_value) == type(r_value) and type(l_value) == int:
            if l_value < r_value:
                return 1
            elif l_value > r_value:
                return -1
        else:
            r_value = r_value if type(r_value) == list else [r_value]
            l_value = l_value if type(l_value) == list else [l_value]
            r = compare_lists(l_value, r_value)
            if r == 0:
                continue
            return r
    return 0


def parse_data(data):
    pairs = [[]]
    for line in data:
        if line == "":
            pairs.append([])
        else:
            pairs[-1].append(eval(line))
    return pairs


def solution1(data):
    pairs = parse_data(data)
    result = 0
    for idx, pair in enumerate(pairs):
        if compare_lists(*pair) == 1:
            result += idx + 1
    return result


def solution2(data):
    pairs = parse_data(data)
    packets = []
    for pair in pairs:
        packets += [*pair]

    decoder_packets = [[[2]], [[6]]]
    packets += decoder_packets

    packets.sort(key=cmp_to_key(compare_lists), reverse=True)
    result = 1
    for idx, packet in enumerate(packets):
        if packet in decoder_packets:
            result *= idx + 1
    return result


solve_problems(13, solution1, solution2)
