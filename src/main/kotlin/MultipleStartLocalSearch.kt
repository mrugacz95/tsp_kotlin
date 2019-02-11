class MultipleStartLocalSearch(iterations: Int = 100) : Solver() {

    override fun getName(): String {
        return "MultipleStartLocalSearch"
    }

    override fun solve(): Solution {
        var bestSolution = Solution(graph.size)
        var bestScore = graph.countLength(bestSolution)
        for (i in 0..iterations) {
            val localSearch = LocalSearch()
            localSearch.graph = graph
            val newSolution = localSearch.solve()
            val newScore = graph.countLength(newSolution)
            if (newScore < bestScore) {
                bestScore = newScore
                bestSolution = newSolution.copy()
            }
        }
        return bestSolution
    }
}