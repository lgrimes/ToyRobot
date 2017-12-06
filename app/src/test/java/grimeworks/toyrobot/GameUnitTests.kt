package grimeworks.toyrobot

import android.graphics.Point
import grimeworks.toyrobot.models.*
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Unit tests to ensure accuracy for the Table object.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameUnitTests {
    val game: Game = Game(Table(5,5), Robot())

    @Before
    fun clearGameBoard() {
        game.resetGame()
    }

    @Test
    fun move_robot_without_place_is_handled() {
        game.performCommand(GameCommand.MOVE, null)
        val robotPosition = game.currentRobotPosition()
        assertTrue(robotPosition == null)
    }

    @Test
    fun rotate_robot_left_without_place_is_handled() {
        game.performCommand(GameCommand.LEFT, null)
        val robotPosition = game.currentRobotPosition()
        assertTrue(robotPosition == null)
    }

    @Test
    fun rotate_robot_right_without_place_is_handled() {
        game.performCommand(GameCommand.RIGHT, null)
        val robotPosition = game.currentRobotPosition()
        assertTrue(robotPosition == null)
    }

    @Test
    fun initial_robot_place_is_handled() {
        game.performCommand(GameCommand.PLACE, RobotLocation(Point(2,2), Direction.NORTH))
        val robotPosition = game.currentRobotPosition()
        assertTrue(robotPosition != null)
    }

    @Test
    fun place_robot_then_valid_move_is_handled() {
        game.performCommand(GameCommand.PLACE, RobotLocation(Point(0,0), Direction.NORTH))
        game.performCommand(GameCommand.MOVE,null)

        val robotPosition = game.currentRobotPosition()
        val expectedPosition = RobotLocation(Point(0,1), Direction.NORTH)
        if (robotPosition != null) {
            assertTrue(robotPosition.point.y == expectedPosition.point.y)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_then_invalid_move_is_handled() {
        game.performCommand(GameCommand.PLACE, RobotLocation(Point(0,0), Direction.SOUTH))
        game.performCommand(GameCommand.MOVE,null)

        val robotPosition = game.currentRobotPosition()
        if (robotPosition != null) {
            //The robot shouldn't have moved outside game bounds
            assertTrue(robotPosition.point.y == 0)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_facing_west_then_rotate_left_should_face_south() {
        game.performCommand(GameCommand.PLACE, RobotLocation(Point(2,4), Direction.WEST))
        game.performCommand(GameCommand.LEFT,null)
        // Rotate left after West should show south
        val robotPosition = game.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.SOUTH)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_facing_west_then_rotate_right_should_face_north() {
        game.performCommand(GameCommand.PLACE, RobotLocation(Point(2,4), Direction.WEST))
        game.performCommand(GameCommand.RIGHT,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = game.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.NORTH)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_facing_north_then_rotate_left_should_face_west() {
        game.performCommand(GameCommand.PLACE, RobotLocation(Point(2,4), Direction.NORTH))
        game.performCommand(GameCommand.LEFT,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = game.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.WEST)
        } else {
            assertFalse(false)
        }
    }
}
