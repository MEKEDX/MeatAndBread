package com.kh.mo.meatandbread.ui.waiting


import android.app.PendingIntent

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.app.AlarmManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Time
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.customPreference
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.time
import com.kh.mo.meatandbread.util.Constants.PREFERENCE_NAME
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.util.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit



class WaitingFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var countDownTextView: TextView
    private lateinit var disposable: Disposable
    private lateinit var prefs: SharedPreferences
    private var periodTime = 60
    val TAG = "TAaaaaaG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alarManager()
        inti(view)
        if (prefs.time == "") {
            prefs.time = Gson().toJson(getFutureTime(periodTime))
        } else {
            Log.d(TAG, "onViewCreated:${prefs.time} ")
        }
        updateTime()
    }

    private fun inti(view: View) {
        progressBar = view.findViewById(R.id.progress_count_down)
        countDownTextView = view.findViewById(R.id.count_down_txt_view)
        progressBar.max = periodTime
        prefs = customPreference(requireActivity(), PREFERENCE_NAME)
    }

    private fun updateTime() {
        disposable = observeTime.subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe({ time ->

            Log.d(TAG, "updateTime: ${time.minutes},${time.seconds}")
            countDownTextView.text = makeTimeString(time.minutes, time.seconds)
            val i = time.minutes * 60 + time.seconds
            progressBar.progress = i

        }, { error -> }, {})
    }


    private fun getFutureTime(remainTime: Int): Date {
        val currentTime = Date()
        return calculateTimeAfterMinutes(currentTime, remainTime)
    }

    private fun calculateTimeAfterMinutes(startTime: Date, minutesToAdd: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = startTime
        calendar.add(Calendar.MINUTE, (minutesToAdd / 60))
        return calendar.time

    }

    private val stopSignal = PublishSubject.create<Unit>()

    private val observeTime: Observable<Time> =

        Observable.interval(1, TimeUnit.SECONDS)
            .takeUntil { periodTime <= 0 }
            .takeUntil(stopSignal)
            .map {
                val minutes = (periodTime / 60)
                val seconds = (periodTime % 60)
                periodTime -= 1
                Time(minutes, seconds)
            }


    private fun makeTimeString(min: Int, secs: Int) = String.format("%02d:%02d", min, secs)


    override fun onResume() {
        super.onResume()
        val time = prefs.time
        val date = Gson().fromJson(time, Date::class.java)
        Log.d(TAG, "Saved Time: ${date.time}")
        val currentTime = Date().time
        val timeDifferenceMillis = date.time - currentTime
        Log.d(TAG, "current Time: ${Date().time} ")

        val timeDifferenceSeconds = timeDifferenceMillis / 1000
        Log.d(TAG, "Remaining Time: $timeDifferenceSeconds")

        if (timeDifferenceSeconds.toInt() > 0) {
            periodTime = timeDifferenceSeconds.toInt()
        } else {
            periodTime = 0
            stopSignal.onNext(Unit)
        }
        Log.d(TAG, "periodTime $periodTime")
        updateTime()
    }

    override fun onPause() {
        super.onPause()
        disposable.dispose()
    }

    private fun alarManager() {
        val alarmManager =
            requireContext().getSystemService(AlarmManager::class.java)
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(
                requireContext(), 0, intent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val alarmTime = System.currentTimeMillis() + periodTime * 1000 // Convert seconds to milliseconds
        alarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,alarmTime , pendingIntent)

//        if (pendingIntent != null && alarmManager != null) {
//            alarmManager.cancel(pendingIntent)
//        }
    }
}

