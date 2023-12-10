fun main() {

    fun part1(lines: List<String>): Int {
        val gameMap = parseGamesMap(lines)
        val results: MutableList<Int> = mutableListOf()
        gameMap.forEach{ (time, recordDistance) ->
            val winningTimes = mutableListOf<Int>()
            for (i in 1 .. time -1) {
                val potentionalDistance = i*(time-i)
                if (potentionalDistance > recordDistance) {
                    winningTimes.add(i)
                }
            }
            results.add(winningTimes.count())
        }
        return results.fold(1) { product, element -> product * element }
    }

    fun part2(lines: List<String>): Int {
        val (time, distance) = parseGame(lines)
        val winningTimes = mutableListOf<Long>()
        for (i in 1 .. time -1) {
            val potentionalDistance = i*(time-i)
            if (potentionalDistance > distance) {
                winningTimes.add(i)
            }
        }
        return winningTimes.count()
    }


    val lines = readInput("Day06")
    val result = part2(lines)
    result.println()

}


fun parseGamesMap(lines: List<String>): Map<Int, Int> {
    val races = lines
        .map { line ->
            val result = line.dropWhile { !it.isDigit() }
                .replace("""\s+""".toRegex(), " ")
                .split(" ")
                .map { it.toInt() }
            result
        }
    val times = races.first()
    val distances = races.last()
    val gamesMap: MutableMap<Int, Int> = mutableMapOf()
    times.forEachIndexed { index, time ->
        val distance = distances[index]
        gamesMap[time] = distance
    }
    return gamesMap
}

fun parseGame(lines: List<String>): Pair<Long, Long> {
    val races = lines
        .map { line ->
            val result = line.dropWhile { !it.isDigit() }
                .replace("""\s+""".toRegex(), "")
            result.toLong()
        }

    return Pair(races.first(), races.last())
}