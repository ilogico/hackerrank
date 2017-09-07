package trees

private data class Tree(val left: Tree?, val index: Int, val right: Tree?)

private fun makeTree(pairs: List<Pair<Int, Int>>, index: Int = 0): Tree? =
        if (index < 0)
            null
        else {
            val (left, right) = pairs[index]
            Tree(makeTree(pairs, left), index + 1, makeTree(pairs, right))
        }

private fun print(tree: Tree?) {
    if (tree != null) {
        print(tree.left)
        print("${tree.index} ")
        print(tree.right)
    }
}

private fun Tree.swap(targetDepth: Int, currentDepth: Int = targetDepth): Tree? =
        if (currentDepth == 1)
            Tree(right?.swap(targetDepth), index, left?.swap(targetDepth))
        else
            Tree(left?.swap(targetDepth, currentDepth - 1), index, right?.swap(targetDepth,currentDepth - 1))

private fun main(args: Array<String>) {
    val nodeCount = readLine()!!.toInt()
    val nodes = (0 until nodeCount)
            .map {
                val pair = readLine()!!.split(' ').map { it.toInt() - 1}
                Pair(pair[0], pair[1])
            }

    var tree = makeTree(nodes) as Tree

    (0 until readLine()!!.toInt()).forEach {
        tree = tree.swap(readLine()!!.toInt()) as Tree
        print(tree)
        println()
    }
}