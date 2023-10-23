package com.kh.mo.meatandbread.ui.waiting

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.kh.mo.meatandbread.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class Waiting : Fragment() {

    private var countDownTimer: CountDownTimer? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var countDownTextView :TextView
    companion object{
         const val CHANNEL_ID ="channel01"
        fun getTimerStringFromDouble(time: Double): String {
            val resultInt = time.roundToInt()

            val mins = resultInt %86400%3600/60
            val secs = resultInt %86400%3600%60
            return makeTimeString( mins , secs)
        }


        private fun makeTimeString( mins: Int, secs: Int)=String.format("%02d:%02d" ,mins , secs)

    }
//    var countForProgress :Int = 0


    private var timerStarted = false
    private lateinit var serviceIntent:Intent
    private var time  = 20.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if(savedInstanceState?.getBoolean("isTimer")!=null)
            timerStarted = savedInstanceState.getBoolean("isTimer")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_count_down)
        countDownTextView = view.findViewById(R.id.count_down_txt_view)



        serviceIntent = Intent(activity?.applicationContext ,TimerService::class.java)
        activity?.registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    override fun onStart() {
        super.onStart()
            if(!timerStarted) {
                startTimer()
            }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isTimer",timerStarted)
    }
    private val updateTime : BroadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent) {

            time =  intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            if(time>0) {
                countDownTextView.text = getTimerStringFromDouble(time)
            }else{
                countDownTextView.text = getTimerStringFromDouble(0.0)

                stopTimer()
                showNotification()


            }
        }

    }

     fun getTimerStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()

        val mins = resultInt %86400%3600/60
        val secs = resultInt %86400%3600%60
        return makeTimeString( mins , secs)
    }


    private fun makeTimeString( mins: Int, secs: Int)=String.format("%02d:%02d" ,mins , secs)

    private fun startTimer(){
        serviceIntent.putExtra(TimerService.TIME_EXTRA ,time)
        activity?.startService(serviceIntent)
        timerStarted= true
    }
    private fun resetTimer(){
        stopTimer()
        time= 0.0
        countDownTextView.text = getTimerStringFromDouble(time)
    }
    private fun resetTimerAfter(time:String){

        serviceIntent.putExtra(TimerService.TIME_EXTRA ,time)
        activity?.startService(serviceIntent)
        timerStarted= true

    }
    private fun startStopTimer(){
        if(timerStarted){
            stopTimer()
        }else{
            startTimer()
        }
    }

    private fun stopTimer() {
        activity?.stopService(serviceIntent)
        timerStarted= false


    }

//    private fun startTimer(milliseconds: Long) {
//        countDownTimer?.cancel()
//        progressBar.max = milliseconds.toInt() / 1000
//        var progress = 0
//        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                val secondsRemaining = (millisUntilFinished / 1000).toInt()
//                progress++
//                progressBar.progress = progress
//
//                val minutes = secondsRemaining / 60
//                val seconds = secondsRemaining % 60
//                val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)
//
//                countDownTextView.text = timeLeftFormatted
//            }
//
//            override fun onFinish() {
//                countDownTextView.text = "00:00"
//                progressBar.progress = 0
//                showNotification()
//                Intent(activity?.applicationContext , TimerService::class.java).also {
//                    it.action = TimerService.Actions.STOP.toString()
//                    activity?.startService(it)
//                }
//            }
//        }
//
//        countDownTimer?.start()
//    }


private fun showNotification(){
    createNotificationChannel()
    val date= Date()
    val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()
    val notificaitonBuilder = NotificationCompat.Builder(activity?.applicationContext ?: requireActivity() , "$CHANNEL_ID")
    notificaitonBuilder.setSmallIcon(R.drawable.notification)
    notificaitonBuilder.setContentTitle("Your Meal")
    notificaitonBuilder.setContentText("Done")
    notificaitonBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
    notificaitonBuilder.setAutoCancel(true)
    val notificationManager = NotificationManagerCompat.from(activity?.applicationContext ?: requireActivity())
    notificationManager.notify(notificationId ,notificaitonBuilder.build())


}
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name : CharSequence ="MyNotification"
            val description  = "My notification channel description"
            val importance  = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID , name , importance)
            notificationChannel.description = description
            val notificationManager = activity?.getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }

}