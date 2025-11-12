package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Random

class PracticalTest01Var07MainActivity : AppCompatActivity() {
    private lateinit var input1: EditText
    private lateinit var input2: EditText
    private lateinit var input3: EditText
    private lateinit var input4: EditText
    private val random = Random()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_main)

//        val serviceIntent = Intent(this, StartedServiceTest::class.java)
//        startService(serviceIntent)

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input4 = findViewById(R.id.input4)

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val message = result.data?.getStringExtra("result")
                if (message != null) {
                    Toast.makeText(this, "Result = " + message, Toast.LENGTH_LONG).show()
                }
            }
            else if (result.resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "The activity returned with result CANCELED", Toast.LENGTH_LONG).show()
            }
        }

        val navigateToSecondaryActivityButton = findViewById<Button>(R.id.set_button)
        navigateToSecondaryActivityButton.setOnClickListener {
            val in1 = input1.text.toString().toDoubleOrNull()
            val in2 = input2.text.toString().toDoubleOrNull()
            val in3 = input3.text.toString().toDoubleOrNull()
            val in4 = input4.text.toString().toDoubleOrNull()

            if (in1 != null && in2 != null && in3 != null && in4 != null) {
                val intent = Intent(this, PracticalTest01Var07SecondaryActivity::class.java)
                intent.putExtra("input1", in1)
                intent.putExtra("input2", in2)
                intent.putExtra("input3", in3)
                intent.putExtra("input4", in4)
                activityResultsLauncher.launch(intent)
            }
        }

        val randomButton = findViewById<Button>(R.id.random_button)
        randomButton.setOnClickListener {
            input1.setText(random.nextDouble(10.0).toString())
            input2.setText(random.nextDouble(10.0).toString())
            input3.setText(random.nextDouble(10.0).toString())
            input4.setText(random.nextDouble(10.0).toString())
        }
    }
}