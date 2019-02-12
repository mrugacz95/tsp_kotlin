open class LocalSearch : Solver() {

    var startSolution: Solution? = null
    override fun getName(): String {
        return "LocalSearch"
    }

    override fun solve(): Solution {
        val solution: Solution = startSolution ?: Solution(graph.size)
        var bestScore = graph.countLength(solution)
        var prevScore: Int
        do {
            prevScore = bestScore
            bestScore += checkNeighbours(solution)
        } while (bestScore < prevScore)
        return solution
    }

    private fun checkNeighbours(solution: Solution): Int {
        for (i in 1 until graph.size) {
            for (j in 0 until i) {
                val (delta, method) = delta(solution, i, j)
                if (delta < 0) {
                    if (method) {
                        solution.swap(i, j)
                    } else {
                        solution.revert(i, j, inclusive = true)
                    }
                    return delta
                }
            }
        }
        return 0
    }


}