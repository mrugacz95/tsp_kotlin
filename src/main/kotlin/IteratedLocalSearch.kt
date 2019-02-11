class IteratedLocalSearch : LocalSearch() {

    override fun getName(): String {
        return "IteratedLocalSearch"
    }

    var time: Long = 0

    init {
        val msls = MultipleStartLocalSearch(100)
        msls.graph = graph
        msls.run()
        time = msls.timings.average().toLong()
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