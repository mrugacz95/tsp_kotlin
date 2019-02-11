import java.util.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Solution {
    private var solution: MutableList<Int>

    constructor(size: Int) {
        solution = MutableList(size) { i -> i }
        shuffle()
    }

    constructor(firstLoop: ArrayList<Int>, secondLoop: ArrayList<Int>) {
        solution = mutableListOf()
        solution.addAll(firstLoop)
        solution.addAll(secondLoop)
    }

    constructor(array: MutableList<Int>) {
        solution = array
    }

    fun firstLoop(): Iterator<Pair<Int, Int>> {
        return object : Iterator<Pair<Int, Int>> {
            var index = 0
            override fun hasNext(): Boolean {
                return index < solution.size / 2
            }

            override fun next(): Pair<Int, Int> {
                val pair = Pair(solution[index], solution[(index + 1) % (solution.size / 2)])
                index++
                return pair
            }
        }
    }

    fun secondLoop(): Iterator<Pair<Int, Int>> {
        return object : Iterator<Pair<Int, Int>> {
            var index = 0
            override fun hasNext(): Boolean {
                return index < solution.size / 2
            }

            override fun next(): Pair<Int, Int> {
                val pair = Pair(
                    solution[index + solution.size / 2],
                    solution[(index + solution.size / 2 + 1) % (solution.size / 2) + solution.size / 2]
                )
                index++
                return pair

            }
        }
    }

    override fun toString(): String {
        return solution.joinToString(", ")
    }


    fun swap(i: Int, j: Int) {
        Collections.swap(solution, i, j)
    }

    operator fun get(i: Int): Int {
        return solution[i]
    }

    fun shuffle() {
        solution.shuffle()
    }

    fun crossover(father: Solution, mother: Solution) {

    }

    fun roll(from: Int, to: Int, shift: Int): Solution {
        val tmp = mutableListOf<Int>().apply { addAll(solution) }.toList()
        for (i in from until to) {
            solution[(i - from + shift) % (to - from) + from] = tmp[i]
        }
        return this
    }

    fun revert(from: Int, to: Int): Solution {
        val start = min(from, to)
        val end = max(to, from) - 1
        for (i in 0..floor((end - start) / 2.0).toInt()) {
            solution[start + i] = solution[end - i].also { solution[end - i] = solution[start + i] }
        }
        return this
    }

    fun perturbation(): Solution {
        // Roll first loop
        roll(0, solution.size / 2, Random().nextInt(solution.size / 2))
        // Roll second loop
        roll(solution.size / 2, solution.size, Random().nextInt(solution.size / 2))
        // Swap first loop
        revert(Random().nextInt(solution.size / 2), Random().nextInt(solution.size / 2))
        revert(
            Random().nextInt(solution.size / 2) + solution.size / 2,
            Random().nextInt(solution.size / 2) + solution.size / 2
        )
        return this
    }

    fun copy(): Solution {
        val newSolution = mutableListOf<Int>().apply { addAll(solution) }
        return Solution(newSolution)
    }


}