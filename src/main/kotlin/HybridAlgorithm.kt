import java.util.*
import kotlin.system.measureTimeMillis

class HybridAlgorithm(
    private val populationPercentage: Double = 0.25,
    private val mutationProbability: Double = 0.1,
    private val crossOverRatio: Double = 0.5
) : Solver() {
    private var time: Long = 0

    override fun prepare() {
        val msls = MultipleStartLocalSearch(100)
        msls.graph = graph
        time = measureTimeMillis {
            msls.solve()
        }
    }

    override fun getName(): String {
        return "HybridAlgorithm"
    }

    private fun initPopulation(): MutableList<Solution> {
        val population = MutableList((graph.size * populationPercentage).toInt()) { Solution(graph.size) }
        for (i in 0 until population.size) {
            val ls = LocalSearch()
            ls.graph = graph
            ls.startSolution = population[i]
            population[i] = ls.solve()

        }
        return population
    }

    override fun solve(): Solution {
        var population = initPopulation()
        val start = System.currentTimeMillis()
        do {
            population = crossOver(population)
            population = mutate(population)
            population = selection(population)
        } while (System.currentTimeMillis() - start < time)
        return sortPopulation(population).first()
    }

    private fun sortPopulation(population: MutableList<Solution>): MutableList<Solution> {
        val withScores = MutableList(population.size) { i -> Pair(population[i], graph.countLength(population[i])) }
        withScores.sortBy { it.second }
        return withScores.map { it.first }.toMutableList()
    }

    private fun crossOver(population: MutableList<Solution>): MutableList<Solution> {
        val largerPopulation = sortPopulation(population)
        for (i in 0 until (population.size * crossOverRatio).toInt()) {
            val father = largerPopulation[Random().nextInt(population.size / 2)]
            val mother = largerPopulation[Random().nextInt(population.size / 2)]
            largerPopulation.add(father.crossover(mother))
        }
        return largerPopulation
    }

    private fun selection(population: MutableList<Solution>): MutableList<Solution> {
        val newPopulation = sortPopulation(population)
        return MutableList((graph.size * populationPercentage).toInt()) { tournamentSelection(newPopulation) }
    }

    private fun tournamentSelection(population: MutableList<Solution>): Solution {
        val tournament = MutableList(5) { population[Random().nextInt((graph.size * populationPercentage).toInt())] }
        return sortPopulation(tournament).first()
    }

    private fun mutate(population: MutableList<Solution>): MutableList<Solution> {
        for (i in 0 until population.size) {
            if (Random().nextDouble() < mutationProbability) {
                population[i] = population[i].mutate()
            }
        }
        return population
    }

}