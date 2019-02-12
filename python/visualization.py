from collections import defaultdict

import glob2
import matplotlib.pyplot as plt
import numpy as np


def distance(n1, n2, nodes):
    _, x1, y1 = nodes[n1]
    _, x2, y2 = nodes[n2]
    return round(((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5)


def read_graph(graph_file):
    nodes = []
    with open(graph_file) as file:
        lines = file.read().split('\n')
    for line in lines[6:-2]:
        nodes.append(list(map(int, line.split())))
    for node_id, x, y in nodes:
        plt.annotate(node_id - 1, (x, y))
    return nodes


def plot_loop(loop, nodes, c='b', start_id=0):
    for node_id in range(len(loop)):
        p1, p2 = loop[node_id - 1], loop[node_id]
        _, x1, y1 = nodes[p1]
        _, x2, y2 = nodes[p2]
        plt.scatter(x2, y2, c=c, s=5)
        plt.plot([x1, x2], [y1, y2], color=c, linewidth=1)


def main():
    averages = defaultdict(list)
    for solution_file in glob2.glob('data/solutions/*.tour'):
        with open(solution_file) as sol:
            lines = sol.read().split("\n")
            all_info = {}
            for line in lines:
                k, v = line.split(':')
                all_info[k] = v
        averages[all_info['graph']].append((float(all_info['min']), all_info['name']))
        graph_file = f'data/{all_info["graph"]}.tsp'
        nodes = read_graph(graph_file)
        solution = list(map(int, all_info['best'].split(', ')))
        first_loop = solution[:int(len(solution) / 2)]
        second_loop = solution[int(len(solution) / 2):]
        plot_loop(first_loop, nodes)
        plot_loop(second_loop, nodes, c='r', start_id=len(first_loop))
        plt.title(f'{all_info["name"]} ({all_info["graph"]})')
        plt.savefig(solution_file.replace('tour', 'png'))
        plt.show()
    for k in averages.keys():
        values, labels = zip(*sorted(averages[k]))
        labels, values = list(labels), list(values)
        pos = np.arange(len(labels))
        plt.bar(pos, values, log=True)
        plt.xticks(pos, labels, rotation='vertical')
        plt.title(f'Comparision - {k}')
        plt.ylabel("Min solution length")
        plt.subplots_adjust(bottom=0.45)
        plt.margins(0.3)
        plt.savefig('comparsion.png')
        plt.show()


if __name__ == '__main__':
    main()
