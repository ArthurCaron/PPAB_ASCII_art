package io.karon

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import javax.imageio.ImageIO

fun Array<CharArray>.saveToTextFile(name: String) {
    var file = getResourceFile(name, "txt")
    var i = 0
    while (file.exists()) {
        file = getResourceFile("${name}_${i++}", "txt")
    }
    val lines = this.flip().map { chars -> chars.fold("") { acc, c -> acc + c + c + c } }
    Files.write(file.toPath(), lines, StandardCharsets.UTF_8)
}

fun Array<CharArray>.saveToImage(font: (Int) -> Font, size: Int, name: String, extension: String, backgroundColor: Color, colorMapper: (col: Int, row: Int) -> Color) {
    val resize: (Int) -> Int = { it * size }
    val bufferedImage = BufferedImage(resize(this.size), resize(this[0].size), BufferedImage.TYPE_INT_RGB)

    val g2d = bufferedImage.createGraphics()
    g2d.font = font(size)
    g2d.color = backgroundColor
    g2d.fillRect(0, 0, resize(this.size), resize(this[0].size))
    for (col in this.indices) {
        for (row in this[0].indices) {
            g2d.color = colorMapper(col, row)
            g2d.drawString(this(col, row).toString(), resize(col), resize(row))
        }
    }
    g2d.dispose()

    writeBufferedImage(bufferedImage, name, extension)
}

fun Array<Array<Double>>.writeImage(name: String, extension: String) {
    writeImage(this.size, this[0].size, name, extension) { col, row ->
        this[col][row].toInt().let { Color(it, it, it) }
    }
}

fun Array<Array<Color>>.writeImage(name: String, extension: String) {
    writeImage(this.size, this[0].size, name, extension) { col, row -> this[col][row] }
}

private fun writeImage(width: Int, height: Int, name: String, extension: String, colorMapper: (Int, Int) -> Color) {
    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

    val g2d = bufferedImage.createGraphics()
    for (col in 0 until width) {
        for (row in 0 until height) {
            g2d.color = colorMapper(col, row)
            g2d.fillRect(col, row, 1, 1)
        }
    }
    g2d.dispose()

    writeBufferedImage(bufferedImage, name, extension)
}

private fun writeBufferedImage(bufferedImage: BufferedImage, name: String, extension: String) {
    var file = getResourceFile(name, extension)
    var i = 0
    while (file.exists()) {
        file = getResourceFile("${name}_${i++}", extension)
    }
    ImageIO.write(bufferedImage, extension, file)
}
