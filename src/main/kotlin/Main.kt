import java.io.File
import java.io.InputStream

fun main() {
    val scan = java.util.Scanner(System.`in`)
    val inputStream: InputStream = File(scan.next()).inputStream()
    val lineList = ArrayList<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }

    val (numberOfOccurences, lineNumbers) = countOccurrences(lineList)

    for (entry in numberOfOccurences.entries) {
        if (entry.value <= 1) {
            continue
        }

        print("Lines with '${entry.key}' : ")
        val indices = lineNumbers[entry.key]!!
        for ((index, lineNumber) in indices.withIndex()) {
            print("$lineNumber")
            if (index != indices.size - 1) {
                print(", ")
            }
        }
        println()
    }
}

data class LiteralsCount(val numberOfOccurrences : Map<String, Int>, val lineNumbers : Map<String, Set<Int>>)

fun countOccurrences(lines: List<String>): LiteralsCount {
    val numberOfOccurrences = HashMap<String, Int>()
    val lineNumbers = HashMap<String, HashSet<Int>>()
    for ((lineNumber, line) in lines.withIndex()) {
        val currList = find(line)
        for (str in currList) {
            if (!numberOfOccurrences.containsKey(str)) {
                numberOfOccurrences[str] = 0
                lineNumbers[str] = HashSet()
            }
            numberOfOccurrences[str] = numberOfOccurrences[str]!! + 1
            lineNumbers[str]!!.add(lineNumber)
        }
    }
    return LiteralsCount(numberOfOccurrences, lineNumbers)
}

fun find(line: String): ArrayList<String> {
    val ans = ArrayList<String>()
    var i = 0
    while (i < line.length) {
        val ch = line[i]
        if (ch != '\'' && ch != '"') {
            i++
            continue
        }

        i++
        var curr = ""
        while (i < line.length && (line[i] != ch || line[i - 1] == '\\')) {
            curr += line[i]
            i++
        }
        if (i < line.length && line[i] == ch) {
            ans.add(curr)
        }
        i++
    }
    return ans
}