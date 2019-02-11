fun main() {
    val graphkro100 = Graph("data/kroA100.tsp")
    val graphkro150 = Graph("data/kroA150.tsp")
    val nn  = NearestNeighbour()
    nn.graph = graphkro100
    nn.start()
    println(nn.results())
    nn.reset()
    nn.graph = graphkro150
    nn.start()
    println(nn.results())
    nn.reset()
    nn.graph
    val nc  = NearestCycle()
    nc.graph = graphkro100
    nc.start()
    println(nc.results())
    nc.reset()
    nc.graph = graphkro150
    nc.start()
    println(nc.results())

    val local = LocalSearch()
    local.graph = graphkro100
    local.start()
    print(local.results())

    local.reset()
    local.graph = graphkro150
    local.start()
    print(local.results())
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