from utils import solve_problems
from typing import List


class DPInfo:
    top: int = -1
    right: int = -1
    bottom: int = -1
    left: int = -1

    def __str__(self):
        return f"({self.top}, {self.right}, {self.bottom}, {self.left})"


def solution1(data):
    dp = []
    trees: List[List[int]] = []

    for line in data:
        for _l in [trees, dp]:
            _l.append([])

        for tree in line:
            trees[-1].append(int(tree))
            dp[-1].append(DPInfo())

    count = len(trees)*2 + len(trees[0])*2 - 4

    for j, line in enumerate(trees):
        for i, height in enumerate(line):
            if j == 0:
                dp[j][i].top = height
            else:
                dp[j][i].top = max(dp[j-1][i].top, height)
            if i == 0:
                dp[j][i].left = height
            else:
                dp[j][i].left = max(dp[j][i-1].left, height)

    for j in range(len(trees) - 1, -1, -1):
        line = trees[j]
        for i in range(len(line) - 1, -1, -1):
            height = trees[j][i]
            if j == len(trees) - 1:
                dp[j][i].bottom = height
            else:
                dp[j][i].bottom = max(dp[j+1][i].bottom, height)
            if i == len(line) - 1:
                dp[j][i].right = height
            else:
                dp[j][i].right = max(dp[j][i+1].right, height)

    for j, line in enumerate(trees):
        if j == 0 or j == len(trees) - 1:
            continue
        for i, height in enumerate(line):
            if i == 0 or i == len(line) - 1:
                continue
            if height > dp[j-1][i].top or height > dp[j+1][i].bottom \
                    or height > dp[j][i-1].left or height > dp[j][i+1].right:
                count += 1

    return count


def solution2(data):
    trees: List[List[int]] = []

    for line in data:
        trees.append([])
        for tree in line:
            trees[-1].append(int(tree))

    def calculate_sum(_trees, _height: int, const_idx: int, _j: bool, _from: int, _to: int, _step: int = 1):
        res = 0
        for idx in range(_from, _to, _step):
            if _j:
                val = trees[idx][const_idx]
            else:
                val = trees[const_idx][idx]
            res += 1
            if val >= _height:
                break
        return res

    result = 0
    for j, line in enumerate(trees):
        for i, height in enumerate(line):
            score = calculate_sum(trees, height, j, False, i+1, len(line)) \
                * calculate_sum(trees, height, i, True, j+1, len(trees)) \
                * calculate_sum(trees, height, j, False, i-1, -1, -1) \
                * calculate_sum(trees, height, i, True, j-1, -1, -1)
            result = max(result, score)
    return result


solve_problems(8, solution1, solution2)
