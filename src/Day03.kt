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

    fun part2(input: List<String>): Int {
        val numberRegex = Regex("""[0-9]+""")
        val symbolRegex = Regex("""\*""")
        var resultingNumbers: MutableList<Int> = mutableListOf()
        input.forEachIndexed { index, line ->
            val allLineGears = symbolRegex.findAll(line).map { Pair(it.range.first, it.range.first) }.toMap()
            val allLineNumbers = numberRegex.findAll(line).map { Triple(it.value.toInt(), it.range.first, it.range.last) }
            allLineGears.values.forEach { gearIndex ->
                gearIndex.println()
                var gearRatioPair: MutableList<Int> = mutableListOf()
                //check left
                if (gearIndex > 0) {
                    val leftNumber = allLineNumbers.firstOrNull { it.third == gearIndex - 1 }
                    if (leftNumber != null) {
                        gearRatioPair.add(leftNumber.first)
                    }
                }
                //check right
                if (gearIndex < line.length - 1) {
                    val rightNumber = allLineNumbers.firstOrNull { it.second == gearIndex + 1 }
                    if (rightNumber != null) {
                        gearRatioPair.add(rightNumber.first)
                    }
                }
                //check above
                if (index > 0) {
                    val aboveLine = input[index - 1]
                    val aboveNumbers = numberRegex.findAll(aboveLine).map { Triple(it.value.toInt(), it.range.first, it.range.last) }
                    for (number in aboveNumbers) {
                        if (gearIndex in number.second - 1..number.third + 1) {
                            gearRatioPair.add(number.first)
                        }
                    }
                }
                //check below
                if (index < input.size - 1) {
                    val belowLine = input[index + 1]
                    val belowNumbers = numberRegex.findAll(belowLine).map { Triple(it.value.toInt(), it.range.first, it.range.last) }
                    for (number in belowNumbers) {
                        if (gearIndex in number.second - 1..number.third + 1) {
                            gearRatioPair.add(number.first)
                        }
                    }
                }
                if (gearRatioPair.size == 2) {
                    resultingNumbers.add(gearRatioPair.reduce { acc, i -> acc * i })
                }
            }
        }
        println("resultingNumbers: $resultingNumbers")
        return resultingNumbers.sum()
    }

    val input = readInput("Day03")
    val result = part2(input)
    println("result = $result")
}