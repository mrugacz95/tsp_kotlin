class NearestNeighbour : Solver() {

    override fun getName(): String {
        return "NearestNeighbour"
    }


    override fun solve(): Solution {
        val (firstLoopNode, secondLoopNode) = graph.getMostDistantNodes()
        val firstLoop = arrayListOf(firstLoopNode)
        val secondLoop = arrayListOf(secondLoopNode)
        val availableNodes = MutableList(graph.size) { i -> i }
        availableNodes.remove(firstLoopNode)
        availableNodes.remove(secondLoopNode)
        while (!availableNodes.isEmpty()) {
            // first loop
            var closestDistance = Int.MAX_VALUE
            var closestNode = firstLoopNode
            for (node in availableNodes) {
                val last = firstLoop.last()
                val newDistance = graph.distance(last, node)
                if (closestDistance > newDistance) {
                    closestDistance = newDistance
                    closestNode = node
                }
            }
            firstLoop.add(closestNode)
            availableNodes.remove(closestNode)
            if (availableNodes.isEmpty()) {
                break
            }
            // second loop
            closestDistance = Int.MAX_VALUE
            closestNode = secondLoopNode
            for (node in availableNodes) {
                val last = secondLoop.last()
                val newDistance = graph.distance(last, node)
                if (closestDistance > newDistance) {
                    closestDistance = newDistance
                    closestNode = node
                }
            }
            secondLoop.add(closestNode)
            availableNodes.remove(closestNode)
        }
        return Solution(firstLoop, secondLoop)
    }

}