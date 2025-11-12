package ro.pub.cs.systems.eim.practicaltest01var07

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class PracticalTest01Var07Service : Service() {
    var processingThread: ProcessingThread? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val CHANNEL_ID = "ColocviuEIM"
        val CHANNEL_NAME = "ForegroundServiceChannel"

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        manager.createNotificationChannel(channel)

        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Colocviu EIM")
            .setContentText("Service is running...")
            .build()

        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        return true
    }

    override fun onRebind(intent: Intent) {
    }

    override fun onDestroy() {
        processingThread!!.stopThread()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: exercise 5 - implement and start the ProcessingThread
        processingThread = ProcessingThread(this)
        processingThread!!.start()
        return START_REDELIVER_INTENT
    }
}
