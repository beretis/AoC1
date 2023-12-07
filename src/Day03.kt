fun main() {
    fun part1(input: List<String>): Int {
        val numberRegex = Regex("""[0-9]+""")
        val symbolRegex = Regex("""[^\d\.\r\n]""")
        var resultingNumbers: MutableList<Int> = mutableListOf()
        input.forEachIndexed { index, line ->
            val allLineNumbers = numberRegex.findAll(line).map { Triple(it.value.toInt(), it.range.first, it.range.last) }
            val allLineSymbols = symbolRegex.findAll(line).map { Pair(it.range.first, it.range.first) }.toMap()
            allLineSymbols.println()
            allLineNumbers.forEach { tripple ->
                val (value, start, end) = tripple
                var numberIsValid = false
                //check left
                if (start > 0) {
                    numberIsValid = numberIsValid || allLineSymbols[start - 1] != null
                }
                //check right
                if (end < line.length - 1) {
                    numberIsValid = numberIsValid || allLineSymbols[end + 1] != null
                }
                //check above
                if (index > 0) {
                    val aboveSymbols = symbolRegex.findAll(input[index - 1]).map { Pair(it.range.first, it.range.first) }.toMap()
                    for (i in start - 1..end + 1) {
                        if (aboveSymbols.containsKey(i)) {
                            numberIsValid = true
                            break
                        }
                    }
                }
                //check below
                if (index < input.size - 1) {
                    val belowSymbols = symbolRegex.findAll(input[index + 1]).map { Pair(it.range.first, it.range.first) }.toMap()
                    for (i in start - 1..end + 1) {
                        if (belowSymbols.containsKey(i)) {
                            numberIsValid = true
                            break
                        }
                    }
                }
                if (numberIsValid) {
                    resultingNumbers.add(value)
                }
            }

        }
        println("resultingNumbers: $resultingNumbers")
        return resultingNumbers.sum()
    }

    val input = readInput("Day03")
    val result = part1(input)
    println("result = $result")
}