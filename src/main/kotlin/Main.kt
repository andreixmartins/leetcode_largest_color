import java.util.*

/*
Explanation:
Graph Construction:

The graph is built as an adjacency list using a MutableList for each node.
The inDegree array is updated to track the number of incoming edges for each node.
Topological Sort (Kahn’s Algorithm):

We initialize a queue with all nodes having zero in-degrees.
Process each node in topological order by dequeuing it and updating its neighbors.
DP Array:

dp[i][c] stores the maximum number of times color c (mapped from the character to index) can be obtained from the path that includes node i.
For each node, we update the maximum count for its color and propagate that value to its neighbors.
Cycle Detection:

If the number of visited nodes is less than n after processing, it means there's a cycle, so we return -1.
Result:

The result is the maximum value found across all nodes and their color counts.
Time Complexity:
O(N + E):
N: Number of nodes (length of colors).
E: Number of edges (length of edges).
Kahn’s algorithm runs in O(N + E), and for each node, we perform constant-time updates of color counts.
Space Complexity:
O(N * 26): We store the dynamic programming table for each node and 26 colors, plus the graph structure and in-degree array.
*/

fun largestPathValue(colors: String, edges: Array<IntArray>): Int {
    val n = colors.length
    val graph = Array(n) { mutableListOf<Int>() }
    val inDegree = IntArray(n)

    // Build the graph and calculate in-degrees
    for (edge in edges) {
        val from = edge[0]
        val to = edge[1]
        graph[from].add(to)
        inDegree[to]++
    }

    // Topological Sort: Kahn's Algorithm
    val queue: LinkedList<Int> = LinkedList()
    val dp = Array(n) { IntArray(26) } // dp[i][c] will store the max color count of node i with color c

    // Initialize the queue with all nodes having zero in-degree
    for (i in 0 until n) {
        if (inDegree[i] == 0) {
            queue.offer(i)
        }
    }

    var visitedNodes = 0
    var result = 0

    // Process the nodes in topological order
    while (queue.isNotEmpty()) {
        val node = queue.poll()
        visitedNodes++
        val colorIndex = colors[node] - 'a'

        // Update the dp value for the current node color
        dp[node][colorIndex] = dp[node][colorIndex] + 1

        // Update the result with the maximum color count
        result = maxOf(result, dp[node][colorIndex])

        // Process all neighbors (children in the graph)
        for (neighbor in graph[node]) {
            // Propagate the maximum color count to the neighbors
            // In the solution provided, the number 26 refers to the number of lowercase English letters (a to z),
            // as each node in the graph has a color represented by a letter from this set.
            // In the problem, each node's color is given as a single lowercase letter (like 'a', 'b', 'c', etc.).
            for (i in 0 until 26) {
                dp[neighbor][i] = maxOf(dp[neighbor][i], dp[node][i])
            }

            // Decrease the in-degree and add to the queue if it's zero
            if (--inDegree[neighbor] == 0) {
                queue.offer(neighbor)
            }
        }
    }

    // If we haven't visited all nodes, there's a cycle in the graph
    return if (visitedNodes == n) result else -1
}


fun main() {
    // Example 1 input
    val colors = "abaca"
    val edges = arrayOf(
        intArrayOf(0, 1),
        intArrayOf(0, 2),
        intArrayOf(2, 3),
        intArrayOf(3, 4)
    )

    // Call the function and print the result
    val result = largestPathValue(colors, edges)
    println("Largest color value: $result")


    // Example 2 input
    val colors2 = "a"
    val edges2 = arrayOf(
        intArrayOf(0, 0)
    )

    // Call the function and print the result
    val result2 = largestPathValue(colors2, edges2)
    println("Largest color value: $result2")
}