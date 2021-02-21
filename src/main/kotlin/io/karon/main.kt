package io.karon

import java.awt.Color


// Project idea from: https://robertheaton.com/2018/06/12/programming-projects-for-advanced-beginners-ascii-art/

fun main(args: Array<String>) {
//    val name = args[0]
    val extension = args[1]
    val pineapple = "ascii-pineapple"
    val tree = "tree_small"
//    val snow = "snow_small"
    val avengers = "avengers"
    val names = listOf(pineapple, tree, avengers)

    names.forEach { name ->
        readImage(name, extension)
//            .also { it.asciiArray.saveToImage(SERIF_BOLD, 9, name, extension, Color.BLACK) { col, row ->
//                showIfHigherThan(it.colorsArray(col, row), 100)
//            } }
            .also { it.asciiArray.saveToImage(SERIF_BOLD, 9, name, extension, Color.BLACK) { col, row ->
                showIfHigherThan(it.colorsArray(col, row), 100, 200, 200)
            } }
            .also { it.asciiArray.saveToImage(SERIF_BOLD, 9, name, extension, Color.BLACK) { col, row ->
                showIfHigherThan(it.colorsArray(col, row), 200, 100, 200)
            } }
            .also { it.asciiArray.saveToImage(SERIF_BOLD, 9, name, extension, Color.BLACK) { col, row ->
                showIfHigherThan(it.colorsArray(col, row), 200, 200, 100)
            } }
    }

//    readImage(name, extension)
//        .also { it.asciiArray.saveToTextFile(name) }
//        .also { it.lightnessArray.writeImage(name, extension) }
//        .also { it.darknessArray.writeImage(name, extension) }
//        .also { it.darknessArray.toAsciiArray().saveToImage(SERIF_BOLD, 9, name, extension, Color.BLACK) { _, _ -> Color.WHITE } }
//        .also { it.darknessArray.toAsciiArray().saveToImage(SERIF_BOLD, 9, name, extension, Color.BLACK) { col, row -> it.colorsArray(col, row) } }
//        .also { it.asciiArray.saveToImage(SERIF_BOLD, 9, name, extension, Color(50, 50, 50)) { col, row -> it.colorsArray(col, row) } }
}


fun showIfHigherThan(color: Color, value: Int): Color =
    if (maxOf(color.red, color.green, color.blue) > value) {
        color
    } else {
        Color.BLACK
    }

fun showIfHigherThan(color: Color, redMin: Int, greenMin: Int, blueMin: Int): Color =
    Color(
        if (color.red > redMin) color.red else 0,
        if (color.green > greenMin) color.green else 0,
        if (color.blue > blueMin) color.blue else 0
    )

