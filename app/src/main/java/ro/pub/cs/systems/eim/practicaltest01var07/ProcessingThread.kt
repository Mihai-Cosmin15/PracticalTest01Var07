package ro.pub.cs.systems.eim.practicaltest01var07

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Process
import android.util.Log
import androidx.annotation.RequiresApi
import java.util.Random

class ProcessingThread(private val context: Context) : Thread() {
    private var isRunning = true

    private val random = Random()

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun run() {
        Log.d("Thread_Process", "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid())
        while (isRunning) {
            sendMessage()
            mySleep(10000)
        }
        Log.d("Thread_Process", "Thread has stopped")
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun sendMessage() {
        val intent = Intent()

        val n1 = random.nextInt(0, 100)
        val n2 = random.nextInt(0, 100)
        val n3 = random.nextInt(0, 100)
        val n4 = random.nextInt(0, 100)

        intent.setAction("ProcessingThread")
        intent.putExtra("n1", n1)
        intent.putExtra("n2", n2)
        intent.putExtra("n3", n3)
        intent.putExtra("n4", n4)

        // Set the target package for cross-app broadcasts
//        val packageName = "ro.pub.cs.systems.eim.colocviutesteim"
//        intent.setPackage(packageName)

        context.sendBroadcast(intent)
    }

    private fun mySleep(ms: Long) {
        try {
            sleep(ms)
        } catch (interruptedException: InterruptedException) {
            interruptedException.printStackTrace()
        }
    }

    fun stopThread() {
        isRunning = false
    }
}