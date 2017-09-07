package trees

fun main(args: Array<String>) {
    val distances = mutableMapOf<Int, MutableMap<Int, Int>>()
    val results = mutableMapOf<Int, MutableMap<Int, Int>>()

    val (nrNodes, nrQueries) = readLine()!!.split(' ').map { it.toInt() }

    (1 until nrNodes).forEach {
        val nodes = readLine()!!.split(' ').map { it.toInt() }.sorted()
        distances.getOrPut(nodes[0], ::mutableMapOf)[nodes[1]] = 1
        distances.getOrPut(nodes[1], ::mutableMapOf)[nodes[0]] = 1
    }

    (1..nrQueries).forEach {
        readLine()
        val set = readLine()!!.split(' ').map { it.toInt() }.sorted()
        var sum = 0
        repeat(set.size) { i ->
            val a = set[i]
            (i + 1 until set.size).forEach { j ->
                sum += results.calc(distances, a, set[j])
                sum %= 1000000007
            }
        }
        println(sum)
    }

}

private fun MutableMap<Int, MutableMap<Int, Int>>.distance(a: Int, b: Int, visited: MutableSet<Int>? = null): Int? {
    if (a == b) {
        return 0
    }
    val distsOfA = getOrPut(a, ::mutableMapOf)
    var result = distsOfA[b]
    if (result != null)
        return result

    val currentlyVisited = visited ?: mutableSetOf()
    currentlyVisited.add(a)

    result = distsOfA.keys.filter { !currentlyVisited.contains(it) }
        .map { distance(it, b, currentlyVisited) ?: -1 }
        .filter { it >= 0 }
        .min()

    if (result != null) {
        result += 1
        distsOfA[b] = result
        this.getOrPut(b, ::mutableMapOf)[a] = result
    }
    return result
}

private fun MutableMap<Int, MutableMap<Int, Int>>.calc(distances: MutableMap<Int, MutableMap<Int, Int>>, a: Int, b: Int) = getOrPut(a, ::mutableMapOf).getOrPut(b) {
    distances.distance(a, b)!! * a * b
}

private class Node(val value: Int) {
    val children = mutableListOf<Node>()
    val childrenDistances = mutableListOf<MutableMap<Int, Int>>()
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