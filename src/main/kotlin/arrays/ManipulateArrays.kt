package arrays

fun solveManipulateArrays() {
    val params = readLine()!!.split(' ').map { it.toInt() }
    val n = params[0]
    val m = params[1]
    var partition: Partition = Leaf(start = 0, end = n, max = 0)
    for (_i in 0 until m) {
        val query = readLine()!!.split(" ").map { it.toInt() }

        val a = query[0]
        val b = query[1]
        val k = query[2].toLong()

        partition = partition.sum(a, b, k)

    }

    println(partition.max)
}

interface Partition {
    val max: Long
    val start: Int
    val end: Int
    fun sum(a: Int, b: Int, k: Long): Partition
}

open class Node(val children: List<Partition>) : Partition {
    override val start = children.first().start
    override val end = children.last().end
    override val max = children.maxBy { it.max }!!.max

    override fun sum(a: Int, b: Int, k: Long) = when {
        a > end || b < start -> this
        a <= start && b >= end -> SumNode(children, k)
        else -> Node(children.map { it.sum(a, b, k) })
    }
}

class SumNode(children: List<Partition>, private val increment: Long) : Node(children) {
    override val max = increment + super.max

    override fun sum(a: Int, b: Int, k: Long) = when {
        a > end || b < start -> this
        a <= start && b >= end -> SumNode(children, k + increment)
        else -> SumNode(children.map { it.sum(a, b, k ) }, increment)
    }
}

data class Leaf(override val start: Int, override val end: Int, override val max: Long) : Partition {
    override fun sum(a: Int, b: Int, k: Long) = when {
        a > end || b < start -> this
        a <= start && b >= end -> copy(max = max + k)
        a <= start -> Node(listOf(copy(end = b, max = max + k), copy(start = b + 1)))
        b >= end -> Node(listOf(copy(end = a - 1), copy(start = a, max = max + k)))
        else -> Node(listOf(copy(end = a - 1), Leaf(start = a, end = b, max = max + k), copy(start = b + 1)))
    }
}

