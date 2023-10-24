package com.kh.mo.meatandbread.ui.waiting

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.kh.mo.meatandbread.R
import java.util.*

class TimerService : Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val time = intent.getDoubleExtra(TIME_EXTRA , 0.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0 ,1000)
       // notify(Waiting.getTimerStringFromDouble(time))
        return START_STICKY
    }

    override fun onDestroy() {
        timer.cancel()

        super.onDestroy()
    }
    private inner class TimeTask(private var time: Double ):TimerTask(){
        override fun run() {
            val intent = Intent(TIMER_UPDATED)
            time--
            intent.putExtra(TIME_EXTRA , time)
            sendBroadcast(intent)

        }


    }
    companion object{
        const val TIMER_UPDATED="TimerUpdated"
        const val TIME_EXTRA ="timeExtra"
    }
    fun notify(time:String){
        val notification  = NotificationCompat.Builder(this ,  WaitingFragment.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Timer is running")
            .setContentText("Elapsed time : $time")
            .build()
        startForeground(1 ,notification)
    }
    enum class Actions{
        START,STOP
    }

}