fun main() {

    fun possibleSubstitiutionsCount(input: List<String>): Int {
        val validWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val dictionary: Map<String, String> = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9")
        var result = 0
        input.forEach { line ->
            val replacable = line.findAnyOf(validWords)
            if (replacable != null) {
                result++
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        for (line in input) {
            val wut = line.filter { it.isDigit() }.let { digits ->
                return@let "${digits.first()}${digits.last()}".toInt()
            }
            wut.println()
            sum += wut
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val validWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val dictionary: Map<String, String> = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9")
//        val mapped = input.map { line ->
//            val replacable = line.findAnyOf(validWords)
//            if (replacable == null) {
//                line
//            } else {
//                line.replace(replacable.second, dictionary[replacable.second]!!)
//            }
//        }

//        possibleSubstitiutionsCount(input).println()
        var resultingArray: List<String> = input
        while (possibleSubstitiutionsCount(resultingArray) > 0) {
            resultingArray = resultingArray.map { line ->
                val replacable = line.findAnyOf(validWords)
                if (replacable == null) {
                    line
                } else {
                    line.replaceFirst(replacable.second, dictionary[replacable.second]!!)
                }
            }
        }
        resultingArray.println()
        val result = part1(resultingArray)
//        result.println()
        return result
    }

    fun part3(input: List<String>): Int {
        val validWords = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val validChars = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val validWhatever = validWords + validChars
        val dictionary: Map<String, String> = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
            )

        var result = 0
        input.forEach {
            var wut = it.findAnyOf(validWhatever)?.second!!
            if (dictionary.containsKey(wut)) {
                wut = dictionary[wut]!!
            }
            var wut2 = it.findLastAnyOf(validWhatever)?.second!!
            if (dictionary.containsKey(wut2)) {
                wut2 = dictionary[wut2]!!
            }
            val value = "$wut$wut2".toInt()
            value.println()
            result += value
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01")
//    part1(testInput).println()

//    part2(testInput).println()
    part3(testInput).println()
}
