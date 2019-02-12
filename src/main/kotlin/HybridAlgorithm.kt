import kotlin.system.measureTimeMillis

class HybridAlgorithm : Solver() {
    private var time: Long = 0

    override fun prepare() {
        val msls = MultipleStartLocalSearch(100)
        msls.graph = graph
        time = measureTimeMillis {
            msls.solve()
        }
    }

    override fun getName(): String {
        return "HybridAlgorithm"
    }

    override fun solve(): Solution {
        val population = MutableList((graph.size * 0.2).toInt()) { Pair(Solution(graph.size), 0) }
        for (i in 0 until population.size) {
            population[i] = Pair(population[i].first, graph.countLength(population[i].first))
        }
        val start = System.currentTimeMillis()
        do {
            population.sortBy { it.second }
            val father = population.random().first
            val mother = population.random().first
            val child = father.crossover(mother)
            val childScore = graph.countLength(child)
            if (childScore > population.last().second) {
                population[population.size - 1] = Pair(child, childScore)
            }
        } while (System.currentTimeMillis() - start < time)
        return population.first().first
    }

}