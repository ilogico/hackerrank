package arrays

fun solveDynamicArrays() {
    val firstLine = readLine()
    val nAndQ = firstLine!!.split(' ').map { it.toInt() }
    val n = nAndQ[0]
    val q = nAndQ[1]
    val seqList = List(n) { mutableListOf<Int>() }
    var lastAnswer = 0
    for (_i in 0..q) {
        val (opCode, x, y) = getAndParseQuery()
        val seq = seqList[(x xor lastAnswer) % n]
        if (opCode == 1) {
            seq.add(y)
        } else {
            lastAnswer = seq[y % seq.size]
            println(lastAnswer)
        }
    }
}

private fun getAndParseQuery(): Triple<Int, Int, Int> {
    val line = readLine()
    val values = line!!.split(' ').map { it.toInt() }
    return Triple(values[0], values[1], values[2])
}
