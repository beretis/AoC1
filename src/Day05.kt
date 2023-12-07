fun main() {

    fun part1(text: String): Int {
        text.lines().first().replace("seeds: ", "").split( " ").map { it.toInt() }.println()
//        text.lines().drop(1).toString().split
        return 0
    }

    val text = readInputAsText("Day05_test")
    val result = part1(text)

}


class TransformationFunction(val id: String) {
    val transformation: Map<IntRange, Int> = mapOf()

    fun getRangeFor(input: Int): IntRange? {
        transformation.keys.forEach { range ->
            if (input in range) {
                return range
            }
        }
        return null
    }

    fun getOffset(input: Int): Int {
        val range = getRangeFor(input)
        if (range != null) {
            return transformation[range]!!
        }
        return input
    }

    fun transform(input: Int): Int {
        return input + getOffset(input)
    }
}

