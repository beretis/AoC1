import kotlin.math.pow

fun main() {
    fun part1(lines: List<String>): Int {
        return lines
            .map { line ->
                val intermediateScratchCard = line.replace("  ", " ")
                    .dropWhile { it != ':' }
                    .drop(2)
                    .split(" | ")
                val winningNumbers = intermediateScratchCard.first().split(" ").map { it.toInt() }
                val yourNumbers = intermediateScratchCard.last().split(" ").map { it.toInt() }
                val winningMultiplicator: Int = winningNumbers.intersect(yourNumbers).count()
                if (winningMultiplicator == 0) {
                    return@map 0
                }
                2.toDouble().pow(winningMultiplicator - 1).toInt()
            }
            .sum()
    }

    fun part2(lines: List<String>): Int {
        val cardWinnings = lines
            .map { line ->
                val intermediateScratchCard = line.replace("  ", " ")
                    .dropWhile { it != ':' }
                    .drop(2)
                    .split(" | ")
                val winningNumbers = intermediateScratchCard.first().split(" ").map { it.toInt() }
                val yourNumbers = intermediateScratchCard.last().split(" ").map { it.toInt() }
                winningNumbers.intersect(yourNumbers).count()
            }

        val winMemory: MutableList<Int> = MutableList(cardWinnings.count()) { 1 }
        cardWinnings.forEachIndexed { index, winValue ->
            if (winValue == 0) {
                return@forEachIndexed
            }
            for (i in index+1 .. index+winValue) {
                val multiplicator = winMemory[index]
                winMemory[i] = winMemory[i] + multiplicator
            }
        }
        println("win: ${winMemory.sum()}")
        return 0
    }


    val text = readInput("Day04")
    val result = part2(text)
    result.println()

}

