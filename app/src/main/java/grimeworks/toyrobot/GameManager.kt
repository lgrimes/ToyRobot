package grimeworks.toyrobot

import grimeworks.toyrobot.models.*

/**
 * Created by laurengrimes on 6/12/17.
 */

interface GameReporterDelegate {
    //Should be implemented by a View/Activity
    fun didReceiveLocationDetails(xPosition: Int, yPosition: Int, direction: Direction)
}

class GameManager(private val table: Table, private val robot: Robot) {
    private var gameReporterDelegate: GameReporterDelegate? = null

    fun setGameReporterDelegate(delegate: GameReporterDelegate) {
        gameReporterDelegate = delegate
    }

    // Convenience function to robot location for testing
    fun currentRobotPosition(): RobotLocation? = robot.currentPosition

    fun parseUserInputCommand(inputString: String): String? {
        if (inputString.contains("MOVE",true)) {
            // Move Command
            performCommand(GameCommand.MOVE, null)
        } else if (inputString.contains("PLACE", true)) {
            // do additional work to check for requested location
            if (inputString.contains(" ")){
                val parametersString = inputString.split(" ")[1]
                val parameters = parametersString.split(',')
                try {
                    val xPosition = parameters[0].toInt()
                    val yPosition = parameters[1].toInt()
                    val direction = parameters[2]
                    val newLocation = RobotLocation(xPosition, yPosition, Direction.valueOf(direction.toUpperCase()))
                    performCommand(GameCommand.PLACE, newLocation)
                } catch (ex: IllegalArgumentException) {
                    return "X and Y must be integers"
                } catch (ex: NumberFormatException) {
                   return "Direction must be NORTH, SOUTH, EAST or WEST"
                }
            }
        } else if (inputString.contains("LEFT", true)) {
            performCommand(GameCommand.LEFT, null)
        } else if (inputString.contains("RIGHT", true)) {
            performCommand(GameCommand.RIGHT, null)
        } else if (inputString.contains("REPORT", true)) {
            performCommand(GameCommand.REPORT, null)
        } else {
            return "Unknown command"
        }
        return null
    }

    // Public function to handle all commands available in the game
    // PLACE: will place the robot at a give X, Y, DIRECTION
    // MOVE: Will move the robot defaultMoveSpaces in the current direction
    // LEFT: Rotate direction counter clockwise
    // RIGHT: Rotate direction clockwise
    fun performCommand(command: GameCommand, requestedLocation: RobotLocation?) {
        when(command) {
            GameCommand.PLACE -> {
                requestedLocation?.let {
                    if (table.isValidLocation(it.xPosition,it.yPosition)) {
                        robot.updateLocation(it)
                    }
                }
            }
            GameCommand.MOVE -> {
                //Requires a current position
                    robot.move()?.let {
                        if (table.isValidLocation(it.xPosition, it.yPosition)) {
                            robot.updateLocation(it)
                        }
                    }
                }
            GameCommand.LEFT, GameCommand.RIGHT -> {
                //Requires a current position
                robot.rotate(command)
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

    // Handy for testing
    fun resetGame() {
        robot.currentPosition = null
    }
}
