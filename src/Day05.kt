fun main() {

    fun part1(text: String): Long {
        val seedsRangesRawArray = text.lines().first().replace("seeds: ", "").split( " ").map { it.toLong() }
        val seedRanges: MutableList<LongRange>  = mutableListOf()
        for (i in 0 until seedsRangesRawArray.size step 2) {
            // Make sure to not get IndexOutOfBoundsException
            seedRanges.add(LongRange(seedsRangesRawArray[i], seedsRangesRawArray[i] + seedsRangesRawArray[i + 1]))
        }

        val transformationMachines: MutableList<TransformationMachine> = mutableListOf()
        text.lines()
            .drop(2)
            .filter { it.isNotEmpty() }
            .map {
                if (it.contains(':')) {
                    return@map "*"
                } else {
                    return@map it
                }
            }
            .forEach {
                if (it == "*") {
                    val transformationFunction = TransformationMachine()
                    transformationMachines.add(transformationFunction)
                } else {
                    val numbers = it.split(" ")
                    val range = numbers[1].toLong() .. numbers[1].toLong() + numbers[2].toLong()
                    val offset = -numbers[0].toLong() + numbers[1].toLong()
                    transformationMachines.last().transformation[range] = offset
                }
            }
        var resultingArrayOfRanges: MutableList<LongRange> = seedRanges
        transformationMachines.forEach { machine ->
            resultingArrayOfRanges = machine.transformRange(resultingArrayOfRanges).toMutableList()
            println("resultArray: $resultingArrayOfRanges")
        }

        return resultingArrayOfRanges.map { it.start }.min()
    }

    val text = readInputAsText("Day05_test")
    val result = part1(text)
    println("result: $result")

}


class TransformationMachine() {
    val transformation: MutableMap<LongRange, Long> = mutableMapOf()

    fun getRangeFor(input: Long): LongRange? {
        transformation.keys.forEach { range ->
            if (input in range) {
                return range
            }
        }
        return null
    }

    fun getOffset(input: Long): Long {
        val range = getRangeFor(input)
        if (range != null) {
            return transformation[range]!!
        }
        return 0
    }

    fun transform(input: Long): Long {
        return input - getOffset(input)
    }

    fun transformRange(inputRanges: List<LongRange>): List<LongRange> {
        val result = inputRanges.flatMap { transformRange(it) }
        return result
    }

    private fun transformRange(inputRange: LongRange): List<LongRange> {
        var splitRanges: MutableList<LongRange> = mutableListOf()
        transformation.forEach { transRange ->
            var intersect = inputRange.intersect(transRange.key)
            if (intersect.isNotEmpty()) {
                val intersectedRange = intersect.min() .. intersect.max()
                if (intersectedRange.count() != inputRange.count()) {
                    if (inputRange.first == intersectedRange.first || inputRange.last == intersectedRange.last) { //Split into two ranges
                        val wtf = inputRange - intersectedRange
                        splitRanges.add(transform(wtf.min()) .. transform(wtf.max()))
                    } else { //Split into 3 ranges
                        splitRanges.add(transform(inputRange.min()) .. transform(intersectedRange.min() - 1))
                        splitRanges.add(transform(intersectedRange.max() + 1) .. transform(inputRange.max()))
                    }
                } else {
                    //not splitting into more ranges
                    splitRanges.add(transform(intersect.min()) .. transform(intersect.max()))
                }
                splitRanges.add(intersectedRange)
            } else {
                println()
            }
        }
        if (splitRanges.isEmpty()) {
            splitRanges.add(inputRange)
        }
        return splitRanges
    }

    override fun toString(): String {
        return transformation.toString()
    }
}

