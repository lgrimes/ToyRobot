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

    // Convenience function to robot location for testing
    fun currentRobotPosition(): RobotLocation? = robot.currentPosition

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
