import java.io.File
import java.util.*

class Solution {
    private var solution: MutableList<Int>

    constructor(size: Int) {
        solution = MutableList(size) { i -> i }
//        solution.shuffle()
    }

    constructor(firstLoop: ArrayList<Int>, secondLoop: ArrayList<Int>){
        solution = mutableListOf()
        solution.addAll(firstLoop)
        solution.addAll(secondLoop)
    }

    fun firstLoop(): Iterator<Pair<Int, Int>> {
        return object : Iterator<Pair<Int, Int>> {
            var index = 0
            override fun hasNext(): Boolean {
                return index < solution.size / 2
            }

            override fun next(): Pair<Int, Int> {
                val pair =  Pair(solution[index], solution[(index + 1) % (solution.size / 2)])
                index ++
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
                val pair =  Pair(solution[index + solution.size / 2], solution[(index + solution.size / 2 + 1) % (solution.size / 2) + solution.size / 2])
                index ++
                return pair

            }
        }
    }

    override fun toString(): String {
        return solution.joinToString(", ")
    }


    fun swap(i: Int, j:Int){
        Collections.swap(solution, i ,j)
    }

    operator fun get(i: Int) : Int{
        return solution[i]
    }

}