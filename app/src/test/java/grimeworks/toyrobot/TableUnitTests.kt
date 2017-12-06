package grimeworks.toyrobot

import grimeworks.toyrobot.models.Table
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests to ensure accuracy for the Table object.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TableUnitTests {
    val table: Table = Table(5,5)

    @Test
    fun outside_table_width_is_handled() {
        assertFalse(table.isValidLocation(6,4))
    }

    @Test
    fun outside_table_height_is_handled() {
        assertFalse(table.isValidLocation(4,10))
    }

    @Test
    fun table_width_and_height_below_zero_is_handled() {
        assertFalse(table.isValidLocation(-1,-10))
    }

    @Test
    fun table_width_below_zero_is_handled() {
        assertFalse(table.isValidLocation(-1,4))
    }

    @Test
    fun table_height_below_zero_is_handled() {
        assertFalse(table.isValidLocation(4,-4))
    }

    @Test
    fun lowest_point_is_handled() {
        assertTrue(table.isValidLocation(0,0))
    }

    @Test
    fun largest_point_is_handled() {
        assertTrue(table.isValidLocation(table.getWidth(),table.getHeight()))
    }
}
