fun main() {
    fun part1(input: List<String>): Int {
        val inputPath = input.first().toCharArray().toList()
        var travelCount = 0
        var finalDestinationReached = false
        var arraydeque = ArrayDeque(inputPath)
        var currentNode= "AAA"
        var knownCrossRoads: MutableMap<String, Pair<String, String>> = mutableMapOf()
        input.drop(2).forEach { line ->
            val regex = Regex("""(\w+)\s*=\s*\((\w+),\s*(\w+)\)""")
            val matchResult = regex.find(line)

            if (matchResult != null) {
                val (key, value1, value2) = matchResult.destructured
                knownCrossRoads[key] = Pair(value1, value2)
            }
        }

        while (finalDestinationReached.not()) {
            val currentChar = arraydeque.removeFirst()
            if (currentChar == 'L') {
                currentNode = knownCrossRoads[currentNode]!!.first
            } else if (currentChar == 'R') {
                currentNode = knownCrossRoads[currentNode]!!.second
            }
            travelCount++
            if (currentNode == "ZZZ") {
                finalDestinationReached = true
            }
            if (arraydeque.isEmpty()) {
                arraydeque = ArrayDeque(inputPath)
            }
        }

        return travelCount
    }

    val input = readInput("Day08")
    val result = part1(input)
    println("result = $result")

}

class CrossRoad(val left: CrossRoad, val right: CrossRoad)