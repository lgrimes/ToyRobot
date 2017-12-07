package grimeworks.toyrobot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import grimeworks.toyrobot.models.Direction

class MainActivity : AppCompatActivity(), GameReporterDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun didReceiveLocationDetails(xPosition: Int, yPosition: Int, direction: Direction) {
        Toast.makeText(this,
                "Robot is currently at X: $xPosition, Y: $yPosition, F: ${direction.name}",
                Toast.LENGTH_SHORT).show()
    }
}
