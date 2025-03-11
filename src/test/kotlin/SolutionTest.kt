import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SolutionTest {

    @Test
    fun largestPathValueExample1Test() {

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
        Assertions.assertEquals(3, result)
    }


    @Test
    fun largestPathValueExample2Test() {

        val solution = Solution()

        // Example 2 input
        val colors2 = "a"
        val edges2 = arrayOf(
            intArrayOf(0, 0)
        )

        // Call the function and print the result
        val result = solution.largestPathValue(colors2, edges2)
        println("Largest color value: $result")
        Assertions.assertEquals(-1, result)
    }
}