fun main() {
    val graphkro100 = Graph("data/kroA100.tsp")
    val graphkro150 = Graph("data/kroA150.tsp")
    val solvers = arrayListOf(
//        NearestNeighbour(),
//        NearestCycle(),
//        LocalSearch(),
//        MultipleStartLocalSearch(),
        IteratedLocalSearch()
    )
    for (solver in solvers) {
        solver.graph = graphkro100
        solver.run()
        solver.saveResults()
        println(solver.results())
        println()
        solver.reset()
        solver.graph = graphkro150
        solver.run()
        solver.saveResults()
        println(solver.results())
        println()
        solver.reset()
    }
//    val testgraph = Graph(100)
//    local.graph = testgraph
//    for (i in 0 until 100){
//        println(local.next(i))
//    }
//    for (i in 0 until 100){
//        println(local.prev(i))
//    }
//    val testSol = Solution(100)
//    val score = testgraph.countLength(testSol)
//    for (i in 1 until 100) {
//        for (j in 0 until 100) {
//            val d =  local.delta(testSol, i , j)
//            testSol.swap(i, j)
//            val newScore = testgraph.countLength(testSol)
//            if(newScore - score != d){
//                throw RuntimeException("$i x $j: $d != ${newScore - score}")
//            }
//            testSol.swap(i, j)
//        }
//    }
}