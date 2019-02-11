import com.sun.org.apache.xpath.internal.operations.Bool

class LocalSearch : Solver() {
    override fun getName(): String {
        return "LocalSearch"
    }

    override fun solve(): Solution {
        val solution = Solution(graph.size)
        var bestScore = graph.countLength(solution)
        var prevScore: Int
        do {
            prevScore = bestScore
            bestScore += checkNeigbours(solution)
        } while (bestScore < prevScore)
        return solution
    }

    fun checkNeigbours(solution: Solution): Int {
        for (i in 1 until graph.size) {
            for (j in 0 until i) {
                val delta = delta(solution, i, j)
                if (delta < 0) {
                    solution.swap(i, j)
                    return delta
                }
            }
        }
        return 0
    }


}