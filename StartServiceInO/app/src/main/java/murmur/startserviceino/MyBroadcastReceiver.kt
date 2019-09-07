package murmur.startserviceino


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

/* test
adb shell am broadcast -a android.telecom.action.ACTION_SHOW_MISSED_CALLS_NOTIFICATION --es NAME "Kanna" --es NUMBER "12345678" -n murmur.startserviceino/murmur.startserviceino.MyBroadcastReceiver
 */
class MyBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val NAME = "NAME"
        const val NUMBER = "NUMBER"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("kanna", "get broadcast ${intent?.getStringExtra("NAME")} +" +
                " ${intent?.getStringExtra("NUMBER")}")
        if (context != null) {
            val input = Data.Builder()
                    .putString(NAME, intent?.getStringExtra(NAME))
                    .putString(NUMBER, intent?.getStringExtra(NUMBER))
                    .build()

            val heavyWorker = OneTimeWorkRequest.Builder(HeavyWorker::class.java)
                    .setInputData(input)
                    .build()

            val notificationWorker = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
                    .setInputData(input)
                    .build()
            WorkManager.getInstance(context)
                    .beginWith(heavyWorker)
                    .then(notificationWorker)
                    .enqueue()
        }

    }
}