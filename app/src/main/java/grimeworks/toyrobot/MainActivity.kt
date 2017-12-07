package grimeworks.toyrobot

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import grimeworks.toyrobot.models.*

class MainActivity : AppCompatActivity(), GameReporterDelegate {
    private var userInputEditText: EditText? = null
    private var executeCommandButton: Button? = null
    private var resetButton: Button? = null
    private val gameManager = GameManager(Table(5,5), Robot())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInputEditText = findViewById(R.id.user_input_et)
        executeCommandButton = findViewById(R.id.execute_command)
        executeCommandButton?.setOnClickListener {
            parseUserInput()
        }

        userInputEditText?.imeOptions = EditorInfo.IME_ACTION_DONE
        userInputEditText?.setOnEditorActionListener { v, actionId, event ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)

            parseUserInput()
            true
        }
        resetButton = findViewById(R.id.reset_command)
        resetButton?.setOnClickListener {
            gameManager.resetGame()
            Toast.makeText(this, "Game has been reset. Please place your robot again", Toast.LENGTH_SHORT).show()
        }

        gameManager.setGameReporterDelegate(this)
    }

    // Check if the input string contain any known commands, then parse and check the format
    private fun parseUserInput() {
        userInputEditText?.editableText?.toString()?.let {
            gameManager.parseUserInputCommand(it)?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

            userInputEditText?.editableText?.clear()
        }

    }

    override fun didReceiveLocationDetails(xPosition: Int, yPosition: Int, direction: Direction) {
        Toast.makeText(this,
                "Robot is currently at X: $xPosition, Y: $yPosition, F: ${direction.name}",
                Toast.LENGTH_SHORT).show()
    }
}
