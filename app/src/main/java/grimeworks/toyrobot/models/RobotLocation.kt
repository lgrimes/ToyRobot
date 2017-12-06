package grimeworks.toyrobot.models

import android.graphics.Point
/**
 * Created by laurengrimes on 6/12/17.
 */

// Used to store location information about the current object on the table
data class RobotLocation(val point: Point, val direction: Direction)