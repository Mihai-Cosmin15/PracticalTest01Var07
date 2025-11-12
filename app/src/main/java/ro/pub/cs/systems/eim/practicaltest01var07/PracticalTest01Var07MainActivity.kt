package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
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
    private var resultNumber = 0.0
    private val random = Random()

    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                val n1 = intent.getIntExtra("n1", 0)
                val n2 = intent.getIntExtra("n2", 0)
                val n3 = intent.getIntExtra("n3", 0)
                val n4 = intent.getIntExtra("n4", 0)

                input1.setText(n1.toString())
                input2.setText(n2.toString())
                input3.setText(n3.toString())
                input4.setText(n4.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var07_main)

        val serviceIntent = Intent(this, PracticalTest01Var07Service::class.java)
        startService(serviceIntent)

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("resultNumber")) {
                resultNumber = savedInstanceState.getDouble("resultNumber")
                Toast.makeText(this, "Saved Result = " + resultNumber.toString(), Toast.LENGTH_LONG).show()
                Log.d("Saved result", resultNumber.toString())
            }
        }

        input1 = findViewById(R.id.input1)
        input2 = findViewById(R.id.input2)
        input3 = findViewById(R.id.input3)
        input4 = findViewById(R.id.input4)

        val activityResultsLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val message = result.data?.getStringExtra("result")
                if (message != null) {
                    Toast.makeText(this, "Result = " + message, Toast.LENGTH_LONG).show()
                    resultNumber = message.toDouble()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        registerReceiver(messageBroadcastReceiver, IntentFilter("ProcessingThread"), Context.RECEIVER_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(messageBroadcastReceiver)
    }

    override fun onDestroy() {
        val intent = Intent(applicationContext, PracticalTest01Var07Service::class.java)
        applicationContext.stopService(intent)
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("resultNumber", resultNumber)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.containsKey("resultNumber")) {
            resultNumber = savedInstanceState.getDouble("resultNumber")
//            Toast.makeText(this, "Saved Result = " + resultNumber.toString(), Toast.LENGTH_LONG).show()
        }
    }
}