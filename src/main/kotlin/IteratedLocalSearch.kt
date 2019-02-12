import kotlin.system.measureTimeMillis

class IteratedLocalSearch : LocalSearch() {

    override fun getName(): String {
        return "IteratedLocalSearch"
    }

    private var time: Long = 0

    override fun prepare() {
        val msls = MultipleStartLocalSearch(100)
        msls.graph = graph
        time = measureTimeMillis {
            msls.solve()
        }
    }

    override fun solve(): Solution {
        val solution = Solution(graph.size)
        val start = System.currentTimeMillis()
        var bestScore = Int.MAX_VALUE
        var bestSolution = solution
        do {
            val localSearch = LocalSearch()
            localSearch.graph = graph
            localSearch.startSolution = solution
            localSearch.run()
            val score = graph.countLength(solution)
            if (score < bestScore) {
                bestScore = score
                bestSolution = solution.copy()
            }
            solution.perturbation()
            solution.perturbation()
        } while (System.currentTimeMillis() - start < time)
        return bestSolution
    }
}