class NearestCycle : Solver() {
    override fun getName(): String {
        return "NearestCycle"
    }

    override fun solve(): Solution {
        val (firstLoopNode, secondLoopNode) = graph.getMostDistantNodes()
        val firstLoop = arrayListOf(firstLoopNode)
        val secondLoop = arrayListOf(secondLoopNode)
        val availableNodes = MutableList(graph.size) { i -> i }
        availableNodes.remove(firstLoopNode)
        availableNodes.remove(secondLoopNode)
        while (!availableNodes.isEmpty()){
            var minCycle = Int.MAX_VALUE
            var minNode = firstLoopNode
            for (node in availableNodes) {
                val last = firstLoop.last()
                val newCycle = graph.distance(firstLoopNode, node) + graph.distance(node, last)
                if (minCycle > newCycle) {
                    minCycle = newCycle
                    minNode = node
                }
            }
            firstLoop.add(minNode)
            availableNodes.remove(minNode)

            if(availableNodes.isEmpty()){
                break
            }
            minCycle = Int.MAX_VALUE
            minNode = secondLoopNode
            for (node in availableNodes) {
                val last = secondLoop.last()
                val newCycle = graph.distance(secondLoopNode, node) + graph.distance(node, last)
                if (minCycle > newCycle) {
                    minCycle = newCycle
                    minNode = node
                }
            }
            secondLoop.add(minNode)
            availableNodes.remove(minNode)
        }
        return Solution(firstLoop, secondLoop)
    }


}