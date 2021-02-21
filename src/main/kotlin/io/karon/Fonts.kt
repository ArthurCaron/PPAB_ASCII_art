package io.karon

import java.awt.Font

val SERIF_PLAIN: (Int) -> Font = { fontSize -> Font("Serif", Font.PLAIN, fontSize) }
val SERIF_BOLD: (Int) -> Font = { fontSize -> Font("Serif", Font.BOLD, fontSize) }
val ARIAL_PLAIN: (Int) -> Font = { fontSize -> Font("Arial", Font.PLAIN, fontSize) }
val ARIAL_BOLD: (Int) -> Font = { fontSize -> Font("Arial", Font.BOLD, fontSize) }
