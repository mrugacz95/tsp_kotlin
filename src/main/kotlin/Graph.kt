import java.io.File
import java.lang.Math.*
import java.lang.StringBuilder
import java.util.*
import kotlin.random.Random

class Graph {

    private lateinit var matrix: Array<IntArray>
    lateinit var name: String
    private lateinit var type: String
    var size: Int = 0
        private set
    private val points = ArrayList<Triple<Int, Double, Double>>()
    constructor(filename: String) {
        File(filename).forEachLine {
            if (it.contains(":")) {
                val (key, value) = it.split(": ")
                when (key) {
                    "NAME" -> name = value
                    "TYPE" -> type = value
                    "DIMENSION" -> size = value.toInt()
                }
            } else if (it.contains(" ")) {
                val (id, x, y) = it.split(" ")
                points.add(Triple(id.toInt() - 1, x.toDouble(), y.toDouble()))
            }
        }
        pointsToDistances()
    }

    constructor(size: Int) {
        this.size = size
        matrix = Array(size) { IntArray(size) }
        for (i in 0 until size) {
            for (j in i + 1 until size) {
                matrix[i][j] = max(i,j) - min(i,j)
                matrix[j][i] = matrix[i][j]
            }
        }
    }

    private fun pointsToDistances() {
        matrix = Array(size) { IntArray(size) }
        for ((id1, x1, y1) in points) {
            for ((id2, x2, y2) in points) {
                matrix[id1][id2] = round(sqrt(pow((x1 - x2), 2.0) + pow((y1 - y2), 2.0))).toInt()
            }
        }
    }

    override fun toString(): String {
        val result = StringBuilder()
        for (row in matrix) {
            for (dist in row) {
                result.append("$dist ")
            }
            result.append("\n")
        }
        return result.toString()
    }

    fun countLength(solution: Solution): Int {
        var lenght = 0
        for ((fistNode, secondNode) in solution.firstLoop()) {
            lenght += matrix[fistNode][secondNode]
        }
        for ((fistNode, secondNode) in solution.secondLoop()) {
            lenght += matrix[fistNode][secondNode]
        }
        return lenght
    }

    fun getMostDistantNodes(): Pair<Int, Int> {
        var max = 0
        var position = Pair(0, 0)
        matrix.forEachIndexed { rowId, row ->
            row.forEachIndexed { cellId, cell ->
                if (cell > max) {
                    max = cell
                    position = Pair(rowId, cellId)
                }
            }
        }
        return position
    }

    fun distance(a: Int, b: Int): Int {
        return matrix[a][b]
    }


}