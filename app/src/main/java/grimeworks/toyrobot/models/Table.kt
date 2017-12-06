package grimeworks.toyrobot.models

import android.graphics.Point

/**
 * Created by laurengrimes on 6/12/17.
 */

class Table(private val width: Int, private val height: Int) {
    private var origin: Point? = null

    fun setOrigin(origin: Point) {
        // Origin requested is outside allowable range
        if ((0..width).contains(origin.x) && (0..height).contains(origin.y)) {
            this.origin = origin
        }
    }
}
