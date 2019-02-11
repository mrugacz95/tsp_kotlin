import sys

import matplotlib.pyplot as plt

nodes = []


def distance(n1, n2):
    _, x1, y1 = nodes[n1]
    _, x2, y2 = nodes[n2]
    return round(((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5)


def main():
    with open(sys.argv[1]) as file:
        lines = file.read().split('\n')
        for line in lines[6:-2]:
            nodes.append(list(map(int, line.split())))
        for node_id, x, y in nodes:
            plt.annotate(node_id - 1, (x, y))
        ids, x, y = zip(*nodes)
        plt.scatter(x, y, c='b', s=5)
    dist = 0
    with open(sys.argv[2]) as sol:
        solution = list(map(int, sol.read().split("\n")))
        first_loop = solution[:len(solution)]
        second_loop = solution[len(solution):]
        # first loop
        for node_id in range(len(first_loop)):
            p1, p2 = first_loop[node_id - 1], first_loop[node_id]
            _, x1, y1 = nodes[p1]
            _, x2, y2 = nodes[p2]
            plt.plot([x1, x2], [y1, y2], c='b', linewidth=1)
            dist += round(((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5)
        # second loop

        for node_id in range(len(second_loop)):
            p1, p2 = second_loop[node_id - 1], second_loop[node_id]
            _, x1, y1 = nodes[p1]
            _, x2, y2 = nodes[p2]
            plt.plot([x1, x2], [y1, y2], c='b')
            dist += round(((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5)
    print(dist)
    # length = 0
    # for node_id in range(int(len(nodes) / 2) - 1):
    #     print (node_id, node_id + 1)
    #     length += distance(node_id, node_id + 1)
    # print (49, 0)
    # length += distance(74, 0)
    # for node_id in range(int(len(nodes) / 2), len(nodes) - 1):
    #     print (node_id, node_id + 1)
    #     length += distance(node_id, node_id + 1)
    #
    # print (99, 50)
    # length += distance(149, 75)
    # print (length)
    title = sys.argv[2].split('/')[2].split('.')
    plt.title(f'{title[-2]} ({title[-3]})')
    plt.savefig("vis.png")
    plt.show()


if __name__ == '__main__':
    main()
