package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var07SecondaryActivity : AppCompatActivity() {
    private lateinit var input1: TextView
    private lateinit var input2: TextView
    private lateinit var input3: TextView
    private lateinit var input4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_secondary)

        input1 = findViewById(R.id.input11)
        input2 = findViewById(R.id.input22)
        input3 = findViewById(R.id.input33)
        input4 = findViewById(R.id.input44)

        val in1 = intent.getDoubleExtra("input1", 0.0)
        val in2 = intent.getDoubleExtra("input2", 0.0)
        val in3 = intent.getDoubleExtra("input3", 0.0)
        val in4 = intent.getDoubleExtra("input4", 0.0)

        input1.text = in1.toString()
        input2.text = in2.toString()
        input3.text = in3.toString()
        input4.text = in4.toString()
        //input4.setText(in1.toString())

        val sumButton = findViewById<Button>(R.id.sum_button)
        sumButton.setOnClickListener {
            val sum = in1 + in2 + in3 + in4
            val intent = Intent()
            intent.putExtra("result", sum.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        val prodButton = findViewById<Button>(R.id.product_button)
        prodButton.setOnClickListener {
            val prod = in1 * in2 * in3 * in4
            val intent = Intent()
            intent.putExtra("result", prod.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}