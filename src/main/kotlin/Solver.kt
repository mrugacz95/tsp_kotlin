import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

abstract class Solver {

    var graph: Graph = Graph(0)
    open var repetions = 10
    var timings = mutableListOf<Long>()
    var scores = mutableListOf<Int>()
    var solutions = mutableListOf<Solution>()

    fun asymmetricDelta(solution: Solution, a: Int, b: Int): Int {
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

    fun symmetricDelta(solution: Solution, a: Int, b: Int): Int {
        var result = 0
        val smaller = min(a, b)
        val greater = max(a, b)
        result -= graph.distance(solution[smaller], solution[prev(smaller)])
        result -= graph.distance(solution[greater], solution[next(greater)])

        result += graph.distance(solution[smaller], solution[next(greater)])
        result += graph.distance(solution[greater], solution[prev(smaller)])
        return result
    }

    fun delta(solution: Solution, a: Int, b: Int): Pair<Int, Boolean> {
        if (next(a) == b || next(b) == a) {
            return Pair(asymmetricDelta(solution, a, b), true)
        }
        if ((a < graph.size / 2 && b < graph.size / 2)
            || (a > graph.size / 2 && b > graph.size / 2)
        ) {
            return Pair(symmetricDelta(solution, a, b), false)
        }
        return Pair(asymmetricDelta(solution, a, b), true)
    }

    fun reset() {
        timings = mutableListOf()
        scores = mutableListOf()
        solutions = mutableListOf()
    }

    protected open fun prepare() {}

    fun run(): Solution {
        prepare()
        for (i in 0 until repetions) {
            var solution: Solution? = null
            val timeElapsed = measureTimeMillis {
                solution = solve()
            }
            solutions.add(solution!!)
            timings.add(timeElapsed)
            scores.add(graph.countLength(solution!!))
        }
        return solutions[scores.indices.maxBy { scores[it] }!!]
    }

    abstract fun solve(): Solution

    fun results(): String {
        val result = StringBuilder()
        result.append("name:${getName()}\n")
        result.append("time:")
        result.append(timings.joinToString(","))
        result.append("\nscore:")
        result.append(scores.joinToString(","))
        val bestSolutionId = scores.indices.maxBy { scores[it] }
        result.append("\nbest:")
        result.append(solutions[bestSolutionId!!])
        result.append("\nmin:")
        result.append("${scores.min()}")
        result.append("\nmax:")
        result.append("${scores.max()}")
        result.append("\navg:")
        result.append("${scores.average()}")
        result.append("\ngraph:")
        result.append(graph.name)
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

    fun saveResults(filePath: String = "data/solutions/${graph.name}.${getName()}.tour") {
        File(filePath).writeText(results())
    }
}