package grimeworks.toyrobot

import grimeworks.toyrobot.models.*

/**
 * Created by laurengrimes on 6/12/17.
 */

interface GameReporterDelegate {
    fun didReceiveLocationDetails(xPosition: Int, yPosition: Int, direction: Direction)
}

class GameManager(private val table: Table, private val robot: Robot) {
    private val defaultMoveSpaces = 1
    private var gameReporterDelegate: GameReporterDelegate? = null

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
                gameReporterDelegate?.apply {
                    robot.currentPosition?.let {
                        this.didReceiveLocationDetails(it.xPosition, it.yPosition, it.direction)
                    }
                }
            }
        }
    }

    fun resetGame() {
        robot.currentPosition = null
    }

    fun currentRobotPosition(): RobotLocation? = robot.currentPosition

    // Check if the requestedLocation is valid on the table, if so, set robot location to it.
    private fun moveRobotTo(requestedLocation: RobotLocation) {
        if (table.isValidLocation(requestedLocation.xPosition,requestedLocation.yPosition)) {
            robot.currentPosition = requestedLocation
        }
    }

    /* If the robots current position is null, the move request is invalid
    *  Then calculate the x,y co-ord of the requested move according to the direction
    *  Only return a point if it is a valid move, otherwise return null
    * */
    private fun isValidMoveRequest(direction: Direction, spaces: Int): RobotLocation? {
        robot.currentPosition?.let {
            val requestedX: Int
            val requestedY: Int
            when(direction){
                Direction.NORTH -> {
                    requestedX = it.xPosition
                    requestedY = it.yPosition + spaces
                }
                Direction.EAST -> {
                    requestedX = it.xPosition + spaces
                    requestedY = it.yPosition
                }
                Direction.SOUTH -> {
                    requestedX = it.xPosition
                    requestedY = it.yPosition-spaces
                }
                Direction.WEST -> {
                    requestedX = it.xPosition - spaces
                    requestedY = it.yPosition
                }
            }
            if (table.isValidLocation(requestedX, requestedY)) {
                return RobotLocation(requestedX, requestedY, direction)
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
            robot.currentPosition = RobotLocation(it.xPosition, it.yPosition, newDirection)
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
