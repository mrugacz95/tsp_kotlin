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

    private fun shuffle() {
        solution.shuffle()
    }

    fun crossover(mother: Solution): Solution { // double OX crossover
        roll(0, solution.size / 2, Random().nextInt(solution.size / 2))
        roll(solution.size / 2, solution.size, Random().nextInt(solution.size / 2))

        var firstLoopEndPoint = Random().nextInt(solution.size / 2)
        var firstLoopStartPoint = Random().nextInt(solution.size / 2)
        if (firstLoopEndPoint < firstLoopStartPoint) {
            firstLoopEndPoint = firstLoopStartPoint.also { firstLoopStartPoint = firstLoopEndPoint }
        }
        val firstLoopLength = firstLoopEndPoint - firstLoopStartPoint
        val firstLoopSubarray = solution.subList(firstLoopStartPoint, firstLoopEndPoint)

        var secondLoopEndPoint = Random().nextInt(solution.size / 2) + solution.size / 2
        var secondLoopStartPoint = Random().nextInt(solution.size / 2) + solution.size / 2
        if (secondLoopEndPoint < secondLoopStartPoint) {
            secondLoopEndPoint = secondLoopStartPoint.also { secondLoopStartPoint = secondLoopEndPoint }
        }
        val secondLoopSubarray = solution.subList(secondLoopStartPoint, secondLoopEndPoint)

        val nodesFromMother = mutableListOf<Int>()
        for (i in 0 until solution.size) {
            val motherNode = mother[i]
            if (!firstLoopSubarray.contains(motherNode) && !secondLoopSubarray.contains(motherNode)) {
                nodesFromMother.add(motherNode)
            }
        }
        val child = nodesFromMother.subList(0, firstLoopStartPoint) +
                firstLoopSubarray +
                nodesFromMother.subList(firstLoopStartPoint, secondLoopStartPoint - firstLoopLength) +
                secondLoopSubarray +
                nodesFromMother.subList(secondLoopStartPoint - firstLoopLength, nodesFromMother.size)
        return Solution(child.toMutableList())
    }

    private fun roll(from: Int, to: Int, shift: Int): Solution {
        val tmp = mutableListOf<Int>().apply { addAll(solution) }.toList()
        for (i in from until to) {
            solution[(i - from + shift) % (to - from) + from] = tmp[i]
        }
        return this
    }

    fun revert(from: Int, to: Int, inclusive: Boolean = false): Solution {
        val start = min(from, to)
        var end = max(to, from)
        if (!inclusive)
            end -= 1
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

    fun mutate(): Solution {
        revert(Random().nextInt(solution.size / 2), Random().nextInt(solution.size / 2))
        revert(
            Random().nextInt(solution.size / 2) + solution.size / 2,
            Random().nextInt(solution.size / 2) + solution.size / 2
        )
        return this
    }

    fun isCorrect(): Boolean {
        for (i in 0 until solution.size) {
            if (!solution.contains(i)) {
                return false
            }
        }
        return true
    }

}