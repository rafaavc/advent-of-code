from utils import solve_problems


def solution1(data):
    current_cycle = 1
    x = 1
    result = 0
    breakpoint = 20
    for line in data:
        if line[:4] == "noop":
            next_cycle = current_cycle + 1
        else:
            next_cycle = current_cycle + 2

        if next_cycle > breakpoint >= current_cycle:
            result += breakpoint * x
            breakpoint += 40

        if line[:4] == "addx":
            x += int(line[5:])
        current_cycle = next_cycle
    return result


def solution2(data):
    current_cycle = 1
    x = 1
    row = 0
    for line in data:
        is_addx = line[:4] == "addx"
        n_cycles = 2 if is_addx else 1

        for _ in range(n_cycles):
            draw = "#" if abs(x + 1 + 40*row - current_cycle) <= 1 else "."
            print(draw, end="")
            if current_cycle % 40 == 0:
                row += 1
                print()
            current_cycle += 1

        if is_addx:
            x += int(line[5:])


solve_problems(10, solution1, solution2)
