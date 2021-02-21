package io.karon

import java.awt.Color
import kotlin.math.pow
import kotlin.math.sqrt

fun Color.toAverageLightness() = ((red + green + blue) / 3).toDouble()

// Photometric/digital ITU BT.709
fun Color.toItuBt709Lightness() = 0.2126 * red + 0.7152 * green + 0.0722 * blue

// Digital ITU BT.601
fun Color.toItuBt601Lightness() = 0.299 * red + 0.587 * green + 0.114 * blue

fun Color.toHsvLightness() = maxOf(red, green, blue).toDouble()

fun Color.toHslLightness() = ((maxOf(red, green, blue) + minOf(red, green, blue)) / 2).toDouble()

fun Color.toHspLightness() = sqrt(0.299 * red.pow(2) + 0.587 * green.pow(2) + 0.114 * blue.pow(2))

fun Color.toPerceivedLightness(): Double {
    val luminance = (0.212655 * gammaEncodedRGBToLinearValue(red)
            + 0.715158 * gammaEncodedRGBToLinearValue(green)
            + 0.072187 * gammaEncodedRGBToLinearValue(blue))
    val perceivedLightness = luminanceToPerceivedLightness(luminance)
    return (perceivedLightness * 255) / 100
}

private fun gammaEncodedRGBToLinearValue(colorChannel: Int): Double {
    val color = colorChannel / 255.0
    return if (color <= 0.04045) {
        color / 12.92
    } else {
        ((color + 0.055) / 1.055).pow(2.4)
    }
}

private fun luminanceToPerceivedLightness(luminance: Double): Double {
    return if (luminance <= (216.0 / 24389.0)) {
        luminance * (24389.0 / 27.0)
    } else {
        luminance.pow(1.0 / 3.0) * 116 - 16
    }
}
