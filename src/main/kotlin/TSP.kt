fun main() {
    val graphkro100 = Graph("data/kroA100.tsp")
    val graphkro150 = Graph("data/kroA150.tsp")
    val graphs = arrayListOf(graphkro100, graphkro150)
    val solvers = arrayListOf<Solver>(
        NearestNeighbour(),
        NearestCycle(),
        LocalSearch(),
        MultipleStartLocalSearch(),
        IteratedLocalSearch(),
        HybridAlgorithm()
    )
    for (solver in solvers) {
        for (graph in graphs) {
            solver.graph = graphkro100
            solver.run()
            solver.saveResults()
            println(solver.results())
            println()
            solver.reset()
        }
    }
    for (i in 0 until 1000) {
        val ls = LocalSearch()
        ls.repetions = 1
        ls.graph = graphkro100
        ls.run()
        ls.saveResults("data/1000LS/$i.tour")
    }
}