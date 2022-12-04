from utils import solve_problems


def get_elf_data(line):
    [elf1, elf2] = line.split(",")
    [l1, r1] = [int(x) for x in elf1.split("-")]
    [l2, r2] = [int(x) for x in elf2.split("-")]
    return [l1, r1, l2, r2]


def problem1(data):
    count = 0
    for line in data:
        [l1, r1, l2, r2] = get_elf_data(line)
        if l1 <= l2 and r1 >= r2 or l2 <= l1 and r2 >= r1:
            count += 1
    return count


def problem2(data):
    count = 0
    for line in data:
        [l1, r1, l2, r2] = get_elf_data(line)
        if l1 <= r2 and r1 >= l2 or l2 <= r1 and r2 >= l1:
            count += 1
    return count


solve_problems(4, problem1, problem2)
