package murmur.startserviceino

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.support.v4.app.NotificationCompat
import android.util.Log
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MyJobService : JobService() {
    private fun createNotification(name: String?, number: String?) {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "my_channel"
        val channel = NotificationChannel(id, "description", NotificationManager.IMPORTANCE_HIGH)
        manager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(this, id)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name)
                .setContentText(number)
        manager.notify(0, builder.build())
    }

    private fun startHeavyJob(params: JobParameters?, name: String?, number: String?) {
        thread(start = true) {
            Log.d("kanna", "start heavy job")
            sleep(10000)
            Log.d("kanna", "finish heavy job")
            createNotification(name, number)
            jobFinished(params, false)
        }
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("kanna", "start job")
        val extras = params?.extras
        Log.d("kanna", "service get ${extras?.getString("NAME")} ${extras?.getString("NUMBER")}")
        startHeavyJob(params, extras?.getString("NAME"), extras?.getString("NUMBER"))
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

}
