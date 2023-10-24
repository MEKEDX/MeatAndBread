package com.kh.mo.meatandbread.ui.waiting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.kh.mo.meatandbread.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class WaitingFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var countDownTextView: TextView
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 20.0


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState?.getBoolean("isTimer") != null)
            timerStarted = savedInstanceState.getBoolean("isTimer")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("tttttt", "onCreateView: ")
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = view.findViewById(R.id.progress_count_down)
        countDownTextView = view.findViewById(R.id.count_down_txt_view)
        progressBar.max = time.toInt()

        serviceIntent = Intent(requireActivity().applicationContext,
            TimerService::class.java)

        requireActivity().registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))
    }

    override fun onStart() {
        super.onStart()
        if (!timerStarted) {
            startTimer()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isTimer", timerStarted)
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent) {

            time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 0.0)
            if (time > 0) {
                countDownTextView.text = getTimerStringFromDouble(time)
                progressBar.progress = progressBar.max - time.roundToInt()
            } else {
                countDownTextView.text = getTimerStringFromDouble(0.0)
                progressBar.progress = 0
                stopTimer()
                showNotification()


            }
        }

    }

    fun getTimerStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()

        val mins = resultInt % 86400 % 3600 / 60
        val secs = resultInt % 86400 % 3600 % 60
        return makeTimeString(mins, secs)
    }


    private fun makeTimeString(mins: Int, secs: Int) = String.format("%02d:%02d", mins, secs)

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        requireActivity().startService(serviceIntent)
        timerStarted = true
    }

    private fun resetTimer() {
        stopTimer()
        time = 0.0
        countDownTextView.text = getTimerStringFromDouble(time)
    }

    private fun startStopTimer() {
        if (timerStarted) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun stopTimer() {
        activity?.stopService(serviceIntent)
        timerStarted = false


    }


    private fun showNotification() {
        createNotificationChannel()
        val date = Date()
        val notificationId = SimpleDateFormat("ddHHmmss", Locale.US).format(date).toInt()
        val notificationBuilder = NotificationCompat.Builder(
            requireActivity().applicationContext,
            "$CHANNEL_ID"
        )
        notificationBuilder.setSmallIcon(R.drawable.notification)
        notificationBuilder.setContentTitle("Your Meal")
        notificationBuilder.setContentText("Done")
        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT
        notificationBuilder.setAutoCancel(true)
        val notificationManager =
            NotificationManagerCompat.from(requireActivity().applicationContext)
        notificationManager.notify(notificationId, notificationBuilder.build())


    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "MyNotification"
            val description = "My notification channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = activity?.getSystemService(
                NotificationManager::class.java
            )
            notificationManager?.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "channel01"
        fun getTimerStringFromDouble(time: Double): String {
            val resultInt = time.roundToInt()

            val mins = resultInt % 86400 % 3600 / 60
            val secs = resultInt % 86400 % 3600 % 60
            return makeTimeString(mins, secs)
        }


        private fun makeTimeString(mins: Int, secs: Int) = String.format("%02d:%02d", mins, secs)

    }

}