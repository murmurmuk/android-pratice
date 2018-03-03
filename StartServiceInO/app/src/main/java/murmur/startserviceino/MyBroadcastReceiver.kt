package murmur.startserviceino

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.PersistableBundle
import android.util.Log

/* test
adb shell am broadcast -a android.telecom.action.ACTION_SHOW_MISSED_CALLS_NOTIFICATION --es NAME "Kanna" --es NUMBER "12345678" -n murmur.startserviceino/murmur.startserviceino.MyBroadcastReceiver
 */
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("kanna", "get broadcast ${intent?.getStringExtra("NAME")} +" +
                " ${intent?.getStringExtra("NUMBER")}")


        val extra = PersistableBundle()
        extra.putString("NAME", intent?.getStringExtra("NAME"))
        extra.putString("NUMBER", intent?.getStringExtra("NUMBER"))

        val builder = JobInfo.Builder(1, ComponentName(context, MyJobService::class.java))
        builder.setOverrideDeadline(100)
        builder.setExtras(extra)

        val scheduler = context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        Log.d("kanna", "put in schedule")
        scheduler.schedule(builder.build())
    }
}