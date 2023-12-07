
fun main() {
    val rMax = 12
    val gMax = 13
    val bMax = 14

    fun part1(gameLines: List<String>): Int {
        val validGames: MutableList<Int> = mutableListOf()
        val possibleGames = gameLines.forEachIndexed { index, gameString ->
            val map: MutableMap<String, Int> = mutableMapOf()
            map["r"] = 0
            map["g"] = 0
            map["b"] = 0
            val gameRounds = gameString
                .drop(gameString.indexOf(':') + 2 )
                .replace(';', ',')
                .replace(", ", ",",)
                .replace("red", "r")
                .replace("green", "g")
                .replace("blue", "b")
                .split(",")
                .forEach { gameElement ->
                    val intermediate = gameElement.split(" ")
                    if (map[intermediate[1]] == null || map[intermediate[1]]!! < intermediate[0].toInt()) {
                        map[intermediate[1]] = intermediate[0].toInt()
                    }
                }
            map.println()
            if ( map["r"]!! <= rMax && map["g"]!! <= gMax && map["b"]!! <= bMax) {
                validGames.add(index + 1)
            }
        }

        validGames.println()
        return validGames.sum()
    }

    fun part2(gameLines: List<String>): Int {
        val results: MutableList<Int> = mutableListOf()
        val possibleGames = gameLines.forEachIndexed { index, gameString ->
            val map: MutableMap<String, Int> = mutableMapOf()
            val gameRounds = gameString
                .drop(gameString.indexOf(':') + 2)
                .replace(';', ',')
                .replace(", ", ",",)
                .replace("red", "r")
                .replace("green", "g")
                .replace("blue", "b")
                .split(",")
                .forEach { gameElement ->
                    val intermediate = gameElement.split(" ")
                    if (map[intermediate[1]] == null || map[intermediate[1]]!! < intermediate[0].toInt()) {
                        map[intermediate[1]] = intermediate[0].toInt()
                    }
                }
            val result = map.values.reduce { acc, colorValue ->
                return@reduce acc*colorValue
            }
            map.println()
            println("result: $result")
            results.add(result)
        }

        return results.sum()
    }

    val testInput = readInput("day02")
    part2(testInput).println()
}
