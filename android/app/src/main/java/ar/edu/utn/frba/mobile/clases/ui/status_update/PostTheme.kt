package ar.edu.utn.frba.mobile.clases.ui.status_update

import androidx.compose.ui.graphics.Color

data class PostTheme(val textColor: Color, val backgroundColors: List<Color>) {
    companion object {

        fun createThemes(): List<PostTheme> {
            return (0..408).map {
                val (firstColor, secondColor) = getBackgroundColors(it)
                val textColor = getTextColor(firstColor)
                val backgroundColors = listOf(firstColor, secondColor).map { Color(it) }
                PostTheme(Color(textColor), backgroundColors)
            }
        }

        fun getBackgroundColors(position: Int): IntArray {
            val color = (android.graphics.Color.WHITE - position * 0xa0ff) or android.graphics.Color.BLACK
            val other = android.graphics.Color.BLACK + (color and 0xffff00 shr 8) + (color and 0xff shl 16)
            return arrayOf(color, other).toIntArray()
        }

        fun getTextColor(backgroundColor: Int): Int {
            val isLight = (((backgroundColor shr 16 and 0xFF) + (backgroundColor shr 8 and 0xFF) + (backgroundColor and 0xFF)) > 382)
            return if (isLight) 0xff shl 24 /* black */ else -1 /* white */
        }
    }
}