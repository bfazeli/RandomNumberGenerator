package com.bfazeli.randomnumbergenerator

import com.bfazeli.randomnumbergenerator.util.dismissKeyBoard
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var lbEditText: EditText
    private lateinit var ubEditText: EditText
    private lateinit var withoutReplacementCheckBox: CheckBox
    private lateinit var outputTextView: TextView
    private lateinit var getNumberButton: Button
    private lateinit var contentView: ConstraintLayout

    private var lastLbInput: Int = -1
    private var lastUbInput: Int = -1
    private var output: Int = -1

    private val numbersWithoutReplacement = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setupViews()
        setupOnClickEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupViews() {
        lbEditText = findViewById(R.id.lbEditText)
        ubEditText = findViewById(R.id.ubEditText)
        withoutReplacementCheckBox = findViewById(R.id.withoutReplacementCheckBox)
        outputTextView = findViewById(R.id.outputTextView)
        getNumberButton = findViewById(R.id.getNumberButton)
        contentView = findViewById(R.id.contentView)
    }

    private fun setupOnClickEvents() {
        getNumberButton.setOnClickListener {
            generateOutput(withoutReplacementCheckBox.isChecked)
        }

        contentView.setOnClickListener {
            this.dismissKeyBoard()
        }

        withoutReplacementCheckBox.setOnCheckedChangeListener { _, _ ->
            reset()
        }
    }

    private fun reset() {
        lbEditText.text.clear()
        ubEditText.text.clear()

        outputTextView.text = "Reset"
    }

    private fun generateOutput(isChecked: Boolean) {
        val lbValue = lbEditText.text.toString().toInt()
        val ubValue = ubEditText.text.toString().toInt()

        if (isChecked) {
            if (lastLbInput != lbValue ||
                    lastUbInput != ubValue) {
                numbersWithoutReplacement.clear()
            } else if (numbersWithoutReplacement.count() == (ubValue - lbValue + 1)) {
                Toast.makeText(applicationContext, "Exhausted generating numbers", Toast.LENGTH_SHORT).show()
                return
            }

            output = -1
            do {
                output = (lbValue..ubValue).random()
            } while (numbersWithoutReplacement.contains(output))

            outputTextView.text = output.toString()
            numbersWithoutReplacement.add(output)

            lastLbInput = lbValue
            lastUbInput = ubValue
        } else {
            outputTextView.text = (lbValue..ubValue).random().toString()
        }
    }
}
