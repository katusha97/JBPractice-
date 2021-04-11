import org.junit.Assert

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
        val (numbers, lines) = countOccurrences(testProg.split("\n"))
        Assert.assertEquals(hashMapOf(Pair("id", 3), Pair("value", 2), Pair("Hello world!", 1)), numbers)
        Assert.assertEquals(hashMapOf(Pair("id", hashSetOf(0, 5)),
            Pair("value", hashSetOf(0, 6)),
            Pair("Hello world!", hashSetOf(2))), lines)
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