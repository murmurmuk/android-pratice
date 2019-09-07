package murmur.startserviceino

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import murmur.startserviceino.MyBroadcastReceiver.Companion.NAME
import murmur.startserviceino.MyBroadcastReceiver.Companion.NUMBER

class NotificationWorker(appContext: Context,
                         workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val id = "my_channel"
        return try {
            val builder = NotificationCompat.Builder(applicationContext, id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(inputData.getString(NAME))
                    .setContentText(inputData.getString(NUMBER))
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(id, "description",
                        NotificationManager.IMPORTANCE_HIGH)
                manager.createNotificationChannel(channel)
            }
            manager.notify(0, builder.build())
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}