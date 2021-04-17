import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import java.util.stream.Stream

fun main() {
    val scan = java.util.Scanner(System.`in`)
    val occurrences = countOccurrences(Files.lines(Path.of(scan.next())))

    for ((key, value) in occurrences.entries) {
        if (setOf(value).size <= 1) {
            continue
        }

        print("Lines with '${key}' : ")
        val indices = value
        for ((index, lineNumber) in indices.withIndex()) {
            print("$lineNumber")
            if (index != indices.size - 1) {
                print(", ")
            }
        }
        println()
    }
}

fun countOccurrences(stream: Stream<String>): Map<String, List<Int>> {
    var lineNumber = 0
    return stream.sequential().flatMap { str ->
        val currIndex = lineNumber++
        find(str).stream().map { occurrence -> Pair(currIndex, occurrence) }
    }
        .collect(Collectors.toMap({ pair -> pair.second }, { pair -> listOf(pair.first) }, { l1, l2 ->
            l1 + l2
        }))
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
        var countSlash = 0
        var curr = ""
        while (i < line.length && (line[i] != ch || countSlash % 2 == 1)) {
            curr += line[i]
            if (line[i] == '\\') {
                countSlash++
            } else {
                countSlash = 0
            }
            i++
        }
        if (i < line.length && line[i] == ch) {
            ans.add(curr)
        }
        i++
    }
    return ans
}