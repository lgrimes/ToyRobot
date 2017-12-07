package grimeworks.toyrobot.models

import android.graphics.Point

/**
 * Created by laurengrimes on 6/12/17.
 */

class Table(private val width: Int, private val height: Int) {

    fun isValidLocation(x: Int, y: Int): Boolean {
        val widthRange = 0..width
        val heightRange = 0..height
        return (widthRange.contains(x) && heightRange.contains(y))
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }
}
