import org.junit.Assert
import java.util.stream.Stream

internal class MainKtTest {

    @org.junit.jupiter.api.Test
    fun testFindOccurrences() {
        val testProg = "database = [{'id': i, \"value\": str(i)} for i in range(100)]\n" +
                "\n" +
                "print(\"Hello world!\")\n" +
                "\n" +
                "for data in database:\n" +
                "    if data['id'] % 10 == 0 or data['id'] == 13:\n" +
                "        print(data['value'])\n"
        val stream = testProg.split("\n").stream()
        val occurrences = countOccurrences(stream)
        Assert.assertEquals(
            hashMapOf(
                Pair("id", arrayListOf(0, 5, 5)),
                Pair("value", arrayListOf(0, 6)),
                Pair("Hello world!", arrayListOf(2))
            ), occurrences
        )
    }

    @org.junit.jupiter.api.Test
    fun testOnSlashes1() {
        val testStr = "print(\"a\\\\\", \"a\\\\\")"
        val stream = Stream.of(testStr)
        val occurrences = countOccurrences(stream)
        Assert.assertEquals(hashMapOf(Pair("a\\\\", arrayListOf(0, 0))), occurrences)
    }

    @org.junit.jupiter.api.Test
    fun testOnSlashes2() {
        val testStr = "print(\"a\\\\\\\"\", \"a\\\\\")"
        val stream = Stream.of(testStr)
        val occurrences = countOccurrences(stream)
        Assert.assertEquals(hashMapOf(Pair("a\\\\\\\"", arrayListOf(0)), Pair("a\\\\", arrayListOf(0))), occurrences)
    }

    @org.junit.jupiter.api.Test
    fun testFind1() {
        val testStr = "database = [{'id': i, \"value\": str(i)} for i in range(100)]"
        val ans = find(testStr)
        val right = arrayListOf("id", "value")
        Assert.assertEquals(right, ans)
    }

    @org.junit.jupiter.api.Test
    fun testFind2() {
        val testStr = "ap.add_argument(\"-l\", \"--limit\", type=int, required=False,\n" +
                "                help='number of \\'lines \"to print', default=1e18)"
        val ans = find(testStr)
        val right = arrayListOf("-l", "--limit", "number of \\'lines \"to print")
        Assert.assertEquals(right, ans)
    }
}