package io.karon

import java.io.File
import kotlin.math.pow

fun getResourceFile(name: String, extension: String) = File("src/main/resources/${name}.$extension")

fun <T> List<T>.prepend(element: T): List<T> {
    val mutableList = this.toMutableList()
    mutableList.add(0, element)
    return mutableList.toList()
}

fun Int.pow(x: Int) = toDouble().pow(x).toInt()

operator fun <T> Array<Array<T>>.invoke(col: Int, row: Int) = this[col][row]

operator fun Array<CharArray>.invoke(col: Int, row: Int) = this[col][row]

fun Array<CharArray>.flip() =
    Array(this[0].size) { col ->
        CharArray(this.size) { row ->
            this[row][col]
        }
    }

// Dumps the last lines/cols if they don't fit exactly
fun Array<Array<Double>>.resize(widthRegroup: Int, heightRegroup: Int): Array<Array<Double>> {
    val moduloWidth = this.size % widthRegroup
    val moduloHeight = this[0].size % heightRegroup
    val newWidth = this.size - moduloWidth
    val newHeight = this[0].size - moduloHeight

    return Array(newWidth / widthRegroup) { col ->
        Array(newHeight / heightRegroup) { row ->
            var newValue = 0.0
            for (colRegroup in 0 until widthRegroup) {
                for (rowRegroup in 0 until heightRegroup) {
                    newValue += this[(col * widthRegroup) + colRegroup][(row * heightRegroup) + rowRegroup]
                }
            }
            newValue / (widthRegroup * heightRegroup)
        }
    }
}

inline fun <T, reified U> Array<Array<T>>.map(func: (col: Int, row: Int) -> U): Array<Array<U>> {
    return Array(this.size) { col ->
        Array(this[0].size) { row ->
            func(col, row)
        }
    }
}

inline fun <reified U> Array<IntArray>.map(func: (col: Int, row: Int) -> U): Array<Array<U>> {
    return Array(this.size) { col ->
        Array(this[0].size) { row ->
            func(col, row)
        }
    }
}
