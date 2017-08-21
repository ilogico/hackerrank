package arrays

fun solveLeftRotation() {
    val firstLine = readLine()
    val d = firstLine!!.split(' ')[1].toInt()
    val array = readLine()!!.split(' ')

    val rotations = d % array.size
    val rotated = array.subList(rotations, array.size) + array.subList(0, rotations)
    println(rotated.joinToString(" "))

}