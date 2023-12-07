fun main() {

    fun part1(text: String): Long {
        val seedsRanges = text.lines().first().replace("seeds: ", "").split( " ").map { it.toLong() }
        val seeds: MutableList<LongRange>  = mutableListOf()
        for (i in 0 until seedsRanges.size step 2) {
            // Make sure to not get IndexOutOfBoundsException
            seeds.add(LongRange(seedsRanges[i], seedsRanges[i] + seedsRanges[i + 1]))
        }

        val transformations: MutableList<TransformationFunction> = mutableListOf()
        val splits = text.lines()
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
                    val transformationFunction = TransformationFunction()
                    transformations.add(transformationFunction)
                } else {
                    val numbers = it.split(" ")
                    val range = numbers[1].toLong() .. numbers[1].toLong() + numbers[2].toLong()
                    val offset = -numbers[0].toLong() + numbers[1].toLong()
                    transformations.last().transformation[range] = offset
                }

            }
        var resultArray: MutableList<Long> = mutableListOf()
        seeds.forEach { range ->
            range.forEach { seed ->
                var intermediateSeed = seed
                transformations.forEach { transformation ->
                    intermediateSeed = transformation.transform(intermediateSeed)
                }
                resultArray.add(intermediateSeed)
            }
        }

        return resultArray.min()
    }

    val text = readInputAsText("Day05")
    val result = part1(text)
    println("result: $result")

}


class TransformationFunction() {
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
        var splitRanges: MutableList<LongRange> = mutableListOf()
        return inputRanges.flatMap { transformRange(it) }
    }

    private fun transformRange(inputRange: LongRange): List<LongRange> {
        var splitRanges: MutableList<LongRange> = mutableListOf()
        transformation.forEach {transRange ->
            var intersect = transRange.key.intersect(inputRange)
            if (intersect.isNotEmpty()) {
                val range = transform(intersect.min()) .. transform(intersect.max())
                splitRanges.add(range)
            }
        }
        return splitRanges
    }

    override fun toString(): String {
        return transformation.toString()
    }
}

