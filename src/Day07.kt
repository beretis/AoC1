fun main() {

    fun part1(input: List<String>): Int {
        val hands = input.map {
            val splits = it.split(" ")
            val result = Pair(splits[0], splits[1].toInt())
            return@map HandAndBid(result.first, result.second)
        }

        val sortedHands = hands.sortedBy { it }
        sortedHands.forEach { println("${it} ${it.getType().rank}") }

        val sum = sortedHands.mapIndexed { index, handAndBid ->
            (index+1) * handAndBid.bid
        }
            .sum()

        return sum
    }

    val input = readInput("Day07")
    val result = part1(input)
    println("result = $result")
}

class HandAndBid(val hand: String, val bid: Int): Comparable<HandAndBid> {

    sealed class HandType(val rank: Int): Comparable<HandType> {

        override fun compareTo(other: HandType): Int {
            return rank-other.rank
        }

        class FiveOfAKind(): HandType(7)
        class FourOfAKind(): HandType(6)
        class FullHouse(): HandType(5)
        class ThreeOfAKind(): HandType(4)
        class TwoPair(): HandType(3)
        class OnePair(): HandType(2)
        class HighCard(): HandType(1)

    }

    val possibleCards = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2").reversed()

    fun getType(): HandType {
        val cardCounts: MutableMap<String, Int> = possibleCards.map { it.to(0) }.toMap().toMutableMap()
        for (card in hand) {
            cardCounts[card.toString()] = cardCounts[card.toString()]!! + 1
        }
        val handMap = cardCounts.filter { it.value > 0 }
        if (handMap.values.count() == 1) {
            return HandType.FiveOfAKind()
        }
        if (handMap.values.count() == 5) {
            return HandType.HighCard()
        }
        if (handMap.values.contains(4)) {
            return HandType.FourOfAKind()
        }
        if (handMap.values.contains(3) && handMap.values.contains(2)) {
            return HandType.FullHouse()
        }
        if (handMap.values.contains(3)) {
            return HandType.ThreeOfAKind()
        }
        if (handMap.values.count() == 3) {
            return HandType.TwoPair()
        }
        if (handMap.values.count() == 4) {
            return HandType.OnePair()
        }

        throw Exception("Unknown hand type")
    }

    fun cardValueAt(index: Int): Int {
        val card = hand[index]
        return possibleCards.indexOf(card.toString())
    }

    override fun compareTo(other: HandAndBid): Int {
        val thisType = this.getType()
        val otherType = other.getType()
        if (thisType.rank != otherType.rank) {
            return thisType.compareTo(otherType)
        }
        for (i in 0..4) {
            val thisCardValue = this.cardValueAt(i)
            val otherCardValue = other.cardValueAt(i)
            if (thisCardValue != otherCardValue) {
                val result = thisCardValue - otherCardValue
                return result
            } else {
                continue
            }
        }
        return 0
    }


    override fun toString(): String {
        return "$hand $bid"
    }
}