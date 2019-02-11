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


def plot_points(graph_file, nodes):
    ids, x, y = zip(*nodes)
    plt.scatter(x, y, c='b', s=5)


def plot_loop(loop, nodes):
    for node_id in range(len(loop)):
        p1, p2 = loop[node_id - 1], loop[node_id]
        _, x1, y1 = nodes[p1]
        _, x2, y2 = nodes[p2]
        plt.plot([x1, x2], [y1, y2], c='b', linewidth=1)


def main():
    averages = []
    for solution_file in glob2.glob('data/solutions/*.tour'):
        with open(solution_file) as sol:
            lines = sol.read().split("\n")
            all_info = {}
            for line in lines:
                k, v = line.split(':')
                all_info[k] = v
        if all_info['graph'] == 'kroA100':
            averages.append((float(all_info['avg']), all_info['name']))
        graph_file = f'data/{all_info["graph"]}.tsp'
        nodes = read_graph(graph_file)
        plot_points(graph_file, nodes)

        solution = list(map(int, all_info['best'].split(', ')))
        first_loop = solution[:len(solution)]
        second_loop = solution[len(solution):]
        # first loop
        plot_loop(first_loop, nodes)
        # second loop
        plot_loop(second_loop, nodes)
        plt.title(f'{all_info["name"]} ({all_info["graph"]})')
        plt.savefig(solution_file.replace('tour', 'png'))
        plt.show()
    values, labels = zip(*sorted(averages))
    labels, values = list(labels), list(values)
    pos = np.arange(len(labels))
    plt.bar(pos, values, log=True)
    plt.xticks(pos, labels, rotation='vertical')
    plt.title('Comparsion')
    plt.subplots_adjust(bottom=0.45)
    plt.margins(0.2)
    plt.savefig('comparsion.png')
    plt.show()


if __name__ == '__main__':
    main()
