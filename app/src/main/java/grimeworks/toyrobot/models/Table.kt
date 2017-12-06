package grimeworks.toyrobot.models

import android.graphics.Point

/**
 * Created by laurengrimes on 6/12/17.
 */

class Table(private val width: Int, private val height: Int) {

    fun isValidLocation(x: Int, y: Int): Boolean {
        return ((0..width).contains(x) && (0..height).contains(y))
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }
}
