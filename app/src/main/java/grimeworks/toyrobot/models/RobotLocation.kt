package grimeworks.toyrobot.models

import android.graphics.Point
/**
 * Created by laurengrimes on 6/12/17.
 */

// Used to store location information about the current object on the table
data class RobotLocation(val xPosition: Int, val yPosition: Int, val direction: Direction)