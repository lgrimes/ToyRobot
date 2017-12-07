package grimeworks.toyrobot.models

/**
 * Created by laurengrimes on 6/12/17.
 */

class Robot {
    var currentPosition: RobotLocation? = null
    private val defaultMoveSpaces = 1

    fun place(location: RobotLocation) {
        currentPosition = location
    }

    /* If the robots current position is null, the move request is invalid
    *  Then calculate the x,y co-ord of the requested move according to the direction
    *  Only return a point if it is a valid move, otherwise return null
    * */
    fun move(): RobotLocation? {
        currentPosition?.let {
            val requestedX: Int
            val requestedY: Int
            when(it.direction){
                Direction.NORTH -> {
                    requestedX = it.xPosition
                    requestedY = it.yPosition + defaultMoveSpaces
                }
                Direction.EAST -> {
                    requestedX = it.xPosition + defaultMoveSpaces
                    requestedY = it.yPosition
                }
                Direction.SOUTH -> {
                    requestedX = it.xPosition
                    requestedY = it.yPosition - defaultMoveSpaces
                }
                Direction.WEST -> {
                    requestedX = it.xPosition - defaultMoveSpaces
                    requestedY = it.yPosition
                }
            }
            return RobotLocation(requestedX, requestedY, it.direction)
        } ?: return null
    }

    // Rotates the robots direction either clockwise (Right) or anti-clockwise (Left)
    fun rotate(command: GameCommand) {
        currentPosition?.let {
            val directionEnumIndex: Int
            when (command) {
                GameCommand.LEFT -> {
                    // Move anti-clockwise (minus array index values)
                    directionEnumIndex = Direction.values().indexOf(it.direction) - 1
                }
                GameCommand.RIGHT -> {
                    // Move anti-clockwise (increment array index values)
                    directionEnumIndex = Direction.values().indexOf(it.direction) + 1
                }
                else -> {
                    //Don't handle any other actions
                    return
                }
            }
            // Direction is in order of rotation, so retrieving index will work.
            val newDirectionEnumValue = evaluateDirectionRequest(directionEnumIndex)
            val newDirection = Direction.values()[newDirectionEnumValue]
            currentPosition = (RobotLocation(it.xPosition, it.yPosition, newDirection))
        }
    }

    // Check if we are still in the bounds of the enum range and accounts for left and right overflow
    private fun evaluateDirectionRequest(directionEnumIndex: Int): Int {
        var requestedDirectionalValue = directionEnumIndex
        if (requestedDirectionalValue < 0) { //Tried to go too far anti-clockwise (left)
            requestedDirectionalValue = Direction.values().count()-1
        }
        if (requestedDirectionalValue >= Direction.values().count()) { //Tried to go too far clockwise (right)
            requestedDirectionalValue = 0
        }
        return requestedDirectionalValue
    }
}
