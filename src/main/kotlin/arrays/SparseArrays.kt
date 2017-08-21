package arrays

fun solveSparseArrays() {
    val n = readLine()!!.toInt()
    val strings = (0 until n).map { readLine()!! }
    val q = readLine()!!.toInt()
    for (_i in 0 until q) {
        val subString = readLine()!!
        println(strings.count { it == subString })
    }
}