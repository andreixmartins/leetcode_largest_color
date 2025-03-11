class Solution {

    fun largestPathValue(colors: String, edges: Array<IntArray>): Int {

        val sizeOfListOfColors = colors.length

        val (graph, inDegrees) = buildGraph(sizeOfListOfColors, edges)

        val (queue, frequencyOfColors) = buildQueue(sizeOfListOfColors, inDegrees, colors)

        return totalFrequentColor(queue, frequencyOfColors, graph, colors, inDegrees, sizeOfListOfColors)
    }

    private fun buildGraph(nVertices: Int, edges: Array<IntArray>): Pair<Map<Int, HashSet<Int>>, IntArray> {
        val graph = HashMap<Int, HashSet<Int>>()
        val degree = IntArray(nVertices) { 0 }

        for ((u, v) in edges) {
            graph.getOrPut(u) { HashSet() }.add(v)
            ++degree[v]
        }

        return Pair(graph, degree)
    }

    private fun buildQueue(
        sizeOfListOfColors: Int,
        inDegrees: IntArray,
        colors: String
    ): Pair<ArrayDeque<Int>, Array<IntArray>> {
        val queue = ArrayDeque<Int>()
        val frequencyOfColors = Array(sizeOfListOfColors) { IntArray(26) }
        for ((idx, inDegree) in inDegrees.withIndex()) {
            if (inDegree == 0) {
                queue.addLast(idx)
                frequencyOfColors[idx][colors[idx] - 'a'] = 1
            }
        }
        return Pair(queue, frequencyOfColors)
    }

    private fun totalFrequentColor(
        queue: ArrayDeque<Int>,
        frequencyOfColors: Array<IntArray>,
        graph: Map<Int, HashSet<Int>>,
        colors: String,
        inDegrees: IntArray,
        sizeOfListOfColors: Int
    ): Int {
        var visitedNode = 0
        var largestColor = 0
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            ++visitedNode

            largestColor = maxOf(largestColor, frequencyOfColors[current].max()!!)

            graph[current]?.let {
                for (next in it) {
                    for (i in 0 until 26) {
                        frequencyOfColors[next][i] =
                            maxOf(
                                frequencyOfColors[next][i],
                                frequencyOfColors[current][i] + if (colors[next] - 'a' == i) 1 else 0
                            )
                    }

                    if (--inDegrees[next] == 0) queue.addLast(next)
                }
            }
        }

        return if (visitedNode == sizeOfListOfColors) largestColor else -1
    }

}

fun main() {

    val solution = Solution()

    // Example 1 input
    val colors = "abaca"
    val edges = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(0, 2),
        intArrayOf(2, 3),
        intArrayOf(3, 4)
    )

    // Call the function and print the result
    val result = solution.largestPathValue(colors, edges)
    println("Largest color value: $result")


    // Example 2 input
    val colors2 = "a"
    val edges2 = arrayOf(
        intArrayOf(0, 0)
    )

    // Call the function and print the result
    val result2 = solution.largestPathValue(colors2, edges2)
    println("Largest color value: $result2")
}