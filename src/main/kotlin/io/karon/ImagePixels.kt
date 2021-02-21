package io.karon

import java.awt.Color
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

class ImagePixels(pixelArray: Array<IntArray>) {
    val colorsArray = pixelArray.toColorsArray()
    val lightnessArray = colorsArray.toLightnessArray { toPerceivedLightness() }
    val darknessArray = lightnessArray.invertLightness()
    val asciiArray = lightnessArray.toAsciiArray()

    private fun Array<IntArray>.toColorsArray() =
        map { col, row -> Color(this[col][row]) }

    private inline fun Array<Array<Color>>.toLightnessArray(lightnessTransform: Color.() -> Double) =
        map { col, row -> lightnessTransform(this[col][row]) }

    private fun Array<Array<Double>>.invertLightness() =
        map { col, row -> 255 - this[col][row] }
}

fun Array<Array<Double>>.toAsciiArray(): Array<CharArray> {
    val lightnessToAscii = "`^\",:;Il!i~+_-?][}{1)(|\\/tfjrxnuvczXYUJCLQ0OZmwqpdbkhao*#MW&8%B@$"
    return Array(this.size) { col ->
        CharArray(this[0].size) { row ->
            val mappedValue = ((this[col][row] * (lightnessToAscii.length - 1)) / 255.0).roundToInt()
            lightnessToAscii[mappedValue]
        }
    }
}
