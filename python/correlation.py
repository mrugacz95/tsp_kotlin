import glob2
import matplotlib.pyplot as plt
import numpy as np


def pairwise(iterable):
    first_loop = iterable[:int(len(iterable) / 2)]
    second_loop = iterable[int(len(iterable) / 2):]
    for i in range(len(first_loop)):
        yield (first_loop[i - 1], first_loop[i])
    for i in range(len(second_loop)):
        yield (second_loop[i - 1], second_loop[i])


def similarity(sol1, sol2):
    pairs = set()
    sim = 0
    for pair in pairwise(sol1):
        pairs.add(pair)
        pairs.add(reversed(pair))
    for pair in pairwise(sol2):
        if pair in pairs:
            sim += 1
    return sim / len(sol1)


def main():
    solutions = []
    scores = []
    for filename in glob2.glob('data/1000LS/*.tour'):
        with open(filename) as file:
            data = file.read().split('\n')
        info = {}
        for line in data:
            k, v = line.split(':')
            info[k] = v
        solution = info['best'].split(', ')
        solutions.append(solution)
        score = int(info['min'])
        scores.append(score)
    best_idx = np.argmax(scores)
    best = scores[best_idx]
    best_solution = solutions[best_idx]
    sim = []
    quality = []
    for idx, solution in enumerate(solutions):
        quality.append(scores[idx] / best)
        sim.append(similarity(best_solution, solution))
    plt.scatter(sim, quality, s=3)
    plt.xlabel('Similarity')
    plt.ylabel('Quality')
    plt.savefig('correlation.png')
    plt.show()


if __name__ == '__main__':
    main()
