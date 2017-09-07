package trees

fun main(args: Array<String>) {
    val distances = mutableMapOf<Int, MutableMap<Int, Int>>()

    val (nrNodes, nrQueries) = readLine()!!.split(' ').map { it.toInt() }

    val nodes = (0 until nrNodes).map { Node(it + 1) }

    (1 until nrNodes).forEach {
        val (parent, child) = readLine()!!.split(' ').map { it.toInt() - 1}
        nodes[parent].children.add(nodes[child])
    }

    val tree = nodes.first()
    tree.populateDistances()

    (1..nrQueries).forEach {
        readLine()
        val set = readLine()!!.split(' ').map { it.toInt() }.sorted()
        var sum = 0
        repeat(set.size) { i ->
            val a = set[i]
            (i + 1 until set.size).forEach { j ->
                val b = set[j]
                sum += a * b * distances.getOrPut(a, ::mutableMapOf).getOrPut(b) {
                    tree.distance(a, b)
                }
                sum %= 1000000007
            }
        }
        println(sum)
    }

}


private class Node(val value: Int) {
    val children = mutableListOf<Node>()
    val childrenDistances = mutableListOf<Map<Int, Int>>()
}

private tailrec fun Node.distance(a: Int, b: Int): Int {
    if (a == value) {
        return childrenDistances.map { it[b] }
                .filterNotNull()
                .first()
    }

    val aIndex = childrenDistances.indexOfFirst { it.containsKey(a) }
    val bIndex = childrenDistances.indexOfFirst { it.containsKey(b) }
    if (aIndex != bIndex)
        return childrenDistances[aIndex][a]!! + childrenDistances[bIndex][b]!!

    return children[aIndex].distance(a, b)
}

private fun Node.populateDistances() {
    children.forEach {
        it.populateDistances()
        childrenDistances.add(mapOf(it.value to 1, *it.childrenDistances.flatMap { it.entries.map { Pair(it.key, it.value + 1) } }.toTypedArray()))
    }

}