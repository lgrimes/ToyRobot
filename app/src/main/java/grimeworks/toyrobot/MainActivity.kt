package grimeworks.toyrobot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import grimeworks.toyrobot.models.Direction

class MainActivity : AppCompatActivity(), GameReporterDelegate {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun didReceiveLocationDetails(xPosition: Int, yPosition: Int, direction: Direction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
