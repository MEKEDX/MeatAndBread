package com.kh.mo.meatandbread.ui.waiting

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import com.kh.mo.meatandbread.MainActivity
import com.kh.mo.meatandbread.R
import java.text.SimpleDateFormat
import java.util.*

class Waiting : Fragment() {

    private var countDownTimer: CountDownTimer? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var countDownTextView :TextView
    private companion object{
        private const val CHANNEL_ID ="channel01"
    }
    var countForProgress :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        showNotification()
        startTimer(30000)
    }

    private fun startTimer(milliseconds: Long) {
        countDownTimer?.cancel()
        progressBar.max = milliseconds.toInt() / 300

        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                val progress = ((milliseconds - millisUntilFinished).toFloat() / milliseconds * 100).toInt()
                progressBar.progress = progress

                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)

                countDownTextView.text = timeLeftFormatted
            }

            override fun onFinish() {
                countDownTextView.text = "00:00"
                progressBar.progress = 0

            }
        }

        countDownTimer?.start()
    }


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
            val importance  = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID , name , importance)
            notificationChannel.description = description
            val notificationManager = activity?.applicationContext?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}