package murmur.startserviceino

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import murmur.startserviceino.MyBroadcastReceiver.Companion.NAME
import murmur.startserviceino.MyBroadcastReceiver.Companion.NUMBER
import java.lang.Thread.sleep

class HeavyWorker(appContext: Context,
                  workerParams: WorkerParameters) : Worker(appContext, workerParams) {


    override fun doWork(): Result {
        return try {
            Log.d("kanna", inputData.getString(NAME) + inputData.getString(NUMBER))

            Log.d("kanna", "start heavy job")
            sleep(10000)
            Log.d("kanna", "finish heavy job")
            Result.success(inputData)
        } catch (e: InterruptedException) {
            Result.failure()
        }
    }
}