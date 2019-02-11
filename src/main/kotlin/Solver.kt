import java.lang.StringBuilder
import kotlin.math.abs
import kotlin.math.min
import kotlin.system.measureTimeMillis

abstract class Solver {

    var graph: Graph = Graph(0)
    var iterations = 10
    var timings = mutableListOf<Long>()
    var scores = mutableListOf<Int>()
    var solutions = mutableListOf<Solution>()

    fun delta(solution: Solution, a: Int, b: Int): Int {
        var result = 0
        result -= graph.distance(solution[a], solution[prev(a)])
        result -= graph.distance(solution[a], solution[next(a)])
        result -= graph.distance(solution[b], solution[prev(b)])
        result -= graph.distance(solution[b], solution[next(b)])

        result += graph.distance(solution[b], solution[prev(a)])
        result += graph.distance(solution[b], solution[next(a)])
        result += graph.distance(solution[a], solution[prev(b)])
        result += graph.distance(solution[a], solution[next(b)])
        if (next(a) == b || next(b) == a) {
            result += 2 * graph.distance(solution[a], solution[b])
        }
        return result
    }

    fun reset() {
        timings = mutableListOf()
        scores = mutableListOf()
        solutions = mutableListOf()
    }

    fun start() {
        for (i in 0..iterations) {
            var solution: Solution? = null
            val timeElapsed = measureTimeMillis {
                solution = solve()
            }
            solutions.add(solution!!)
            timings.add(timeElapsed)
            scores.add(graph.countLength(solution!!))
        }
    }

    protected abstract fun solve(): Solution

    fun results(): String {
        val result = StringBuilder()
        result.append("name: ${getName()}\n")
        result.append("time: ")
        result.append(timings.joinToString(","))
        result.append("\nscore:")
        result.append(scores.joinToString(","))
        val bestSolutionId = scores.indices.maxBy { scores[it] }
        result.append("\nbest: ")
        result.append(solutions[bestSolutionId!!])
        result.append("\nmin: ")
        result.append("${scores.min()}")
        result.append("\nmax: ")
        result.append("${scores.max()}")
        result.append("\navg: ")
        result.append("${scores.average()}")
        result.append("\ngraph: ")
        result.append(graph.name)
        result.append("\n\n")
        return result.toString()
    }

    abstract fun getName(): String

    fun next(i: Int): Int {
        if (i < graph.size / 2) {
            return (i + 1) % (graph.size / 2)
        }
        return (i + 1) % (graph.size / 2) + graph.size / 2
    }

    fun prev(i: Int): Int {
        if (i < graph.size / 2) {
            return (i - 1 + graph.size) % (graph.size / 2)
        }
        return (i - 1 + graph.size) % (graph.size / 2) + graph.size / 2
    }
}