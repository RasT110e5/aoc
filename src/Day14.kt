fun main() {
    val day = 14
    println(Day14(readTest(day)).part1())
    println(Day14(readTest(day)).part2())

    println("Answers:")
    println("Part 1=" + Day14(readInput(day)).part1())
    println("Part 2=" + Day14(readInput(day)).part2())
}

class Day14(private val input: List<String>) {
    private val flowDirections = listOf(0 to 1, -1 to 1, 1 to 1)
    private val sandPouringPoint = 500 to 0
    private var floor = 0

    private fun parseRockLines(input: List<String>): Set<Pair<Int, Int>> {
        val rockLineBounds = input.asSequence().flatMap { row -> row.toNumbersWithin().windowed(4, 2) }
        val rockLines = rockLineBounds.flatMap { (xFrom: Int, yFrom: Int, xTo: Int, yTo: Int) ->
            //Only horizontal and vertical rock lines
            if (xFrom == xTo) (yFrom.anyDirectionalRange(yTo)).map { xFrom to it }
            else (xFrom.anyDirectionalRange(xTo)).map { it to yFrom }
        }.toSet()
        floor = rockLines.maxOf { it.second }
        return rockLines
    }

    fun part1(): Int {
        val rocks = parseRockLines(input)

        val pouredSand = mutableSetOf<Pair<Int, Int>>()
        var sandGrain = sandPouringPoint
        while (sandGrain.y < floor) {
            val possiblePositions = flowDirections.map { sandGrain.x + it.x to sandGrain.y + it.y }
                .filterNot { it in rocks || it in pouredSand }
            sandGrain = evaluateMovement(possiblePositions, pouredSand, sandGrain)
        }
        return pouredSand.size
    }

    fun part2(): Int {
        val rocks = parseRockLines(input)

        val pouredSand = mutableSetOf<Pair<Int, Int>>()
        var sandGrain = sandPouringPoint
        while (sandPouringPoint !in pouredSand) {
            val possiblePositions = flowDirections.map { sandGrain.x + it.x to sandGrain.y + it.y }
                .filterNot { it in rocks || it in pouredSand || it.second == floor + 2 }
            sandGrain = evaluateMovement(possiblePositions, pouredSand, sandGrain)
        }
        return pouredSand.size
    }

    private fun evaluateMovement(
        possiblePositions: List<Pair<Int, Int>>,
        pouredSand: MutableSet<Pair<Int, Int>>,
        sandGrain: Pair<Int, Int>
    ): Pair<Int, Int> {
        return if (possiblePositions.isEmpty()) {
            pouredSand += sandGrain
            sandPouringPoint
        } else possiblePositions.first()
    }
}