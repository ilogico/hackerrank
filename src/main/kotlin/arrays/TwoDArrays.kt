package arrays

fun solve2DArray() {
    val matrix = (1..6).map { readLine()!!.split(' ').map { it.toInt() }}

    val max = (0..3).flatMap { x -> (0..3).map { y ->
        (0..2).map { matrix[y][x + it]}.sum() +
                matrix[y + 1][x + 1] +
                (0..2).map { matrix[y + 2][x + it]}.sum()
    }}.max()
    println(max)
}