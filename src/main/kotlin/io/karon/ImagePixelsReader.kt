package io.karon

import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.awt.image.DataBufferInt
import javax.imageio.ImageIO

fun readImage(name: String, extension: String): ImagePixels {
    val image = ImageIO.read(getResourceFile(name, extension)) ?: throw Exception("Cannot read image")
    println("The image size is ${image.width} x ${image.height}")

    val dataBuffer = image.raster?.dataBuffer ?: throw Exception("Something is null :)")

    val pixelArray = when (dataBuffer) {
        is DataBufferByte -> dataBuffer.data.convertTo2DIntArray(image)
        is DataBufferInt -> dataBuffer.data.convertTo2DIntArray(image)
        else -> throw Exception("Unhandled DataBuffer type")
    }

    return ImagePixels(pixelArray)
}

private fun ByteArray.convertTo2DIntArray(image: BufferedImage): Array<IntArray> {
    val hasAlphaChannel = image.alphaRaster != null

    return this.toList()
        .let {
            if (hasAlphaChannel) {
                it.chunked(4)
            } else {
                it.chunked(3)
                    .map { chunk -> chunk.prepend(0xff.toByte()) }
            }
        }
        .computeIntFromARGB()
        .convertTo2DIntArray(image)
}

private fun List<List<Byte>>.computeIntFromARGB() =
    this.map { chunk -> chunk.map { it.toInt() } }
        .map { chunk ->
            var argb = chunk[0] and 0xff shl 24 // alpha
            argb += chunk[1] and 0xff // blue
            argb += chunk[2] and 0xff shl 8 // green
            argb += chunk[3] and 0xff shl 16 // red
            argb
        }.toIntArray()

private fun IntArray.convertTo2DIntArray(image: BufferedImage): Array<IntArray> {
    val pixelArray = Array(image.width) { IntArray(image.height) }

    for (col in 0 until image.width) {
        for (row in 0 until image.height) {
            val pos = row * image.width + col
            pixelArray[col][row] = this[pos]
        }
    }

    return pixelArray
}
