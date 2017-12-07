package grimeworks.toyrobot

import grimeworks.toyrobot.models.Direction
import grimeworks.toyrobot.models.GameCommand
import grimeworks.toyrobot.models.Robot
import grimeworks.toyrobot.models.RobotLocation
import org.junit.Test
import org.junit.Assert.*
/**
 * Created by laurengrimes on 7/12/17.
 */
class RobotUnitTests {
    private val robot = Robot()

    @Test
    fun test_that_robot_cannot_move_if_no_place_command() {
        val newLocation = robot.move()
        assert(newLocation == null)
    }

    @Test
    fun test_that_robot_cannot_rotate_left_if_no_place_command() {
        robot.rotate(GameCommand.LEFT)
        assert(robot.currentPosition == null)
    }

    @Test
    fun test_that_robot_cannot_rotate_right_if_no_place_command() {
        robot.rotate(GameCommand.RIGHT)
        assert(robot.currentPosition == null)
    }

    @Test
    fun test_that_robot_cannot_accept_other_non_rotate_command() {
        robot.rotate(GameCommand.MOVE)
        assert(robot.currentPosition == null)
    }

    @Test
    fun test_that_robot_will_accept_place_command() {
        robot.place(RobotLocation(2,3,Direction.NORTH))
        assert(robot.currentPosition != null)
    }

    @Test
    fun test_that_robot_will_accept_place_command_then_move() {
        robot.place(RobotLocation(2,3,Direction.NORTH))
        robot.move()
        robot.currentPosition?.let {
            assert(it.yPosition == 4)
        } ?: assertFalse(false)

    }
}