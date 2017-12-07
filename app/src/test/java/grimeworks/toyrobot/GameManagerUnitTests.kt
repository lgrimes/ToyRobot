package grimeworks.toyrobot

import grimeworks.toyrobot.models.*
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests to ensure accuracy for the GameManager object.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameManagerUnitTests {

    @Test
    fun move_robot_without_place_is_handled() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.MOVE, null)
        val robotPosition = gameManager.currentRobotPosition()
        assertTrue(robotPosition == null)
    }

    @Test
    fun rotate_robot_left_without_place_is_handled() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.LEFT, null)
        val robotPosition = gameManager.currentRobotPosition()
        assertTrue(robotPosition == null)
    }

    @Test
    fun rotate_robot_right_without_place_is_handled() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.RIGHT, null)
        val robotPosition = gameManager.currentRobotPosition()
        assertTrue(robotPosition == null)
    }

    @Test
    fun initial_robot_place_is_handled() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(2,2, Direction.NORTH))
        val robotPosition = gameManager.currentRobotPosition()
        assertTrue(robotPosition != null)
    }

    @Test
    fun place_robot_then_valid_move_is_handled() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(0,0, Direction.NORTH))
        gameManager.performCommand(GameCommand.MOVE,null)

        val robotPosition = gameManager.currentRobotPosition()
        val expectedPosition = RobotLocation(0,1, Direction.NORTH)
        if (robotPosition != null) {
            assertTrue(robotPosition.yPosition == expectedPosition.yPosition)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_then_invalid_move_is_handled() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(0,0, Direction.SOUTH))
        gameManager.performCommand(GameCommand.MOVE,null)

        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            //The robot shouldn't have moved outside gameManager bounds
            assertTrue(robotPosition.yPosition == 0)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_facing_west_then_rotate_left_should_face_south() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(2,4, Direction.WEST))
        gameManager.performCommand(GameCommand.LEFT,null)
        // Rotate left after West should show south
        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.SOUTH)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_facing_west_then_rotate_right_should_face_north() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(2,4, Direction.WEST))
        gameManager.performCommand(GameCommand.RIGHT,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.NORTH)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun place_robot_facing_north_then_rotate_left_should_face_west() {
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(2,4, Direction.NORTH))
        gameManager.performCommand(GameCommand.LEFT,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.WEST)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun test_that_provided_example_one_passes() {
//        PLACE 0,0,NORTH MOVE REPORT Output: 0,1,NORTH
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(0,0, Direction.NORTH))
        gameManager.performCommand(GameCommand.MOVE,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.NORTH && robotPosition.xPosition == 0 && robotPosition.yPosition == 1)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun test_that_provided_example_two_passes() {
//        PLACE 0,0,NORTH MOVE REPORT Output: 0,1,NORTH
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(0,0, Direction.NORTH))
        gameManager.performCommand(GameCommand.LEFT,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.WEST && robotPosition.xPosition == 0 && robotPosition.yPosition == 0)
        } else {
            assertFalse(false)
        }
    }

    @Test
    fun test_that_provided_example_three_passes() {
//        PLACE 0,0,NORTH MOVE REPORT Output: 0,1,NORTH
        val gameManager: GameManager = GameManager(Table(5, 5), Robot())
        gameManager.performCommand(GameCommand.PLACE, RobotLocation(1,2, Direction.EAST))
        gameManager.performCommand(GameCommand.MOVE,null)
        gameManager.performCommand(GameCommand.MOVE,null)
        gameManager.performCommand(GameCommand.LEFT,null)
        gameManager.performCommand(GameCommand.MOVE,null)
        // Check the logic for right rotation from west because west
        // is end of an array, test the flow back to the beginning
        val robotPosition = gameManager.currentRobotPosition()
        if (robotPosition != null) {
            assertTrue(robotPosition.direction == Direction.NORTH && robotPosition.xPosition == 3 && robotPosition.yPosition == 3)
        } else {
            assertFalse(false)
        }
    }
}
