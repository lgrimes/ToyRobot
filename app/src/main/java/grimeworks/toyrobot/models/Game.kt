package grimeworks.toyrobot.models

import android.graphics.Point

/**
 * Created by laurengrimes on 6/12/17.
 */

class Game(private val table: Table, private val robot: Robot) {
    private val defaultMoveSpaces = 1

    // Public function to handle all commands available in the game
    // PLACE: will place the robot at a give X, Y, DIRECTION
    // MOVE: Will move the robot defaultMoveSpaces in the current direction
    // LEFT: Rotate direction counter clockwise
    // RIGHT: Rotate direction clockwise
    fun performCommand(command: GameCommand, requestedLocation: RobotLocation?) {
        when(command) {
            GameCommand.PLACE -> {
                requestedLocation?.let {
                    moveRobotTo(it)
                }
            }
            GameCommand.MOVE -> {
                //Requires a current position
                robot.currentPosition?.let {
                    isValidMoveRequest(it.direction, defaultMoveSpaces)?.let {
                        moveRobotTo(it)
                    }
                }
            }
            GameCommand.LEFT, GameCommand.RIGHT -> {
                //Requires a current position
                robot.currentPosition?.let {
                    rotateRobotDirection(command)
                }
            }
            GameCommand.REPORT -> {
                //Requires a current position
            }
        }
    }

    fun resetGame() {
        robot.currentPosition = null
    }

    fun currentRobotPosition(): RobotLocation? {
        return robot.currentPosition
    }

    // Check if the requestedLocation is valid on the table, if so, set robot location to it.
    private fun moveRobotTo(requestedLocation: RobotLocation) {
        if (table.isValidLocation(requestedLocation.point.x,requestedLocation.point.y)) {
            robot.currentPosition = requestedLocation
        }
    }

    /* If the robots current position is null, the move request is invalid
    *  Then calculate the x,y co-ord of the requested move according to the direction
    *  Only return a point if it is a valid move, otherwise return null
    * */
    private fun isValidMoveRequest(direction: Direction, spaces: Int): RobotLocation? {
        robot.currentPosition?.let {
            val requestedPoint: Point
            when(direction){
                Direction.NORTH -> {
                    requestedPoint = Point(it.point.x, it.point.y + spaces)
                }
                Direction.EAST -> {
                    requestedPoint = Point(it.point.x + spaces, it.point.y)
                }
                Direction.SOUTH -> {
                    requestedPoint = Point(it.point.x, it.point.y-spaces)
                }
                Direction.WEST -> {
                    requestedPoint = Point(it.point.x - spaces, it.point.y)
                }

            }
            if (table.isValidLocation(requestedPoint.x, requestedPoint.y)) {
                return RobotLocation(requestedPoint, direction)
            } else {
                return null
            }
        } ?: return null
    }

    // Rotates the robots direction either clockwise (Right) or anti-clockwise (Left)
    private fun rotateRobotDirection(command: GameCommand) {
        robot.currentPosition?.let {
            val newDirectionEnumValue: Int
            if (command == GameCommand.LEFT) {
                // Move anti-clockwise (minus array index values)
                newDirectionEnumValue = evaluateDirectionRequest(Direction.values().indexOf(it.direction) - 1)
            } else  {
                // Move anti-clockwise (increment array index values)
                newDirectionEnumValue = evaluateDirectionRequest(Direction.values().indexOf(it.direction) + 1)
            }
            // Direction is in order of rotation, so retrieving index will work.
            val newDirection = Direction.values()[newDirectionEnumValue]
            robot.currentPosition = RobotLocation(it.point, newDirection)
        }
    }

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
