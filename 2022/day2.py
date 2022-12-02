from utils import solve_problems

rules = {
    "A": {
        "X": 3,
        "Y": 6,
        "Z": 0
    },
    "B": {
        "X": 0,
        "Y": 3,
        "Z": 6
    },
    "C": {
        "X": 6,
        "Y": 0,
        "Z": 3
    }
}

play_score = {
    "X": 1,
    "Y": 2,
    "Z": 3
}


def problem1(data):
    score = 0
    for line in data:
        [opponent_play, my_play] = line.split(" ")
        score += rules[opponent_play][my_play] + play_score[my_play]
    return score


plays = {
    "A": {
        "X": "C",
        "Y": "A",
        "Z": "B"
    },
    "B": {
        "X": "A",
        "Y": "B",
        "Z": "C"
    },
    "C": {
        "X": "B",
        "Y": "C",
        "Z": "A"
    }
}

outcome_score = {
    "X": 0,
    "Y": 3,
    "Z": 6
}

play_score_2 = {
    "A": 1,
    "B": 2,
    "C": 3
}


def problem2(data):
    score = 0
    for line in data:
        [opponent_play, outcome] = line.split(" ")
        play = plays[opponent_play][outcome]
        score += outcome_score[outcome] + play_score_2[play]
    return score


solve_problems(2, problem1, problem2)

