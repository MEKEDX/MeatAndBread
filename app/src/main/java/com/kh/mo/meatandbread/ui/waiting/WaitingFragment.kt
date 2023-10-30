package com.kh.mo.meatandbread.ui.waiting


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.model.Time
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.customPreference
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.isTimeClear
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.progress
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.time
import com.kh.mo.meatandbread.util.Constants.PREFERENCE_NAME
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit


class WaitingFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var countDownTextView: TextView
    private var disposable: Disposable? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager
    private var periodTime = 0L
    private var periodTimeByMinuets = 0
    private val stopSignal = PublishSubject.create<Unit>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiView(view)
        intiSharedPreference()
        receiveTimeOfCookingMeals()

        saveProgressAndTimeOfCooking()
        putIntiValueForProgressBar()
        checkTimeForMeal()

    }

    private fun checkTimeForMeal(){
        getSavedTimeOfEndOfCookingMeals()
            .takeIf { it>0 }?.let {
                alarManager(it)
            }
    }
    private fun saveProgressAndTimeOfCooking() {
        if (periodTime > 0L) {
            savedProgressOfTime(periodTime.toInt())
            savedTimeOfEndOfCookingMeals()
        }
    }

    private fun checkIfStillRemainTime() {
        if (isTimerEmpty() != true) {
            updateProgressBarForNewTime()
        } else {
            timerIsEnd()
        }
    }

    private fun updateProgressBarForNewTime() {
        val secondsRemainingTime = getSecondsRemainingTime()
        if (secondsRemainingTime > 0) {
            periodTime = secondsRemainingTime
            updateUI()

        }
    }


    override fun onResume() {
        super.onResume()
        checkIfStillRemainTime()
    }

    private fun alarManager(futureTime:Long) {
        alarmManager = requireContext().getSystemService(AlarmManager::class.java)
        val intent = Intent(requireActivity(), AlarmReceiver::class.java)
        pendingIntent =
            PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            futureTime
           ,
            pendingIntent
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_waiting, container, false)
    }

    private fun intiView(view: View) {
        progressBar = view.findViewById(R.id.progress_count_down)
        countDownTextView = view.findViewById(R.id.count_down_txt_view)
    }

    private fun makeTimeString(min: Int, secs: Int) = String.format("%02d:%02d", min, secs)

    private fun receiveTimeOfCookingMeals() {
        periodTimeByMinuets = WaitingFragmentArgs.fromBundle(requireArguments()).timeOfMeals
        periodTime = (periodTimeByMinuets * 60).toLong()

    }

    private fun putIntiValueForProgressBar() {
        progressBar.max = getSavedProgressOfTime()
    }

    private fun intiSharedPreference() {
        prefs = customPreference(requireActivity(), PREFERENCE_NAME)
    }

    private fun getTimeOfEndOfCookingMeals(): Date {
        Calendar.getInstance().apply {
            add(Calendar.MINUTE, periodTimeByMinuets)
            return this.time

        }
    }

    private fun getSavedTimeOfEndOfCookingMeals(): Long {
        return if (prefs.time != "") {
            Gson().fromJson(prefs.time, Date::class.java).time
        } else 0L
    }

    private fun getCurrentTime() = Calendar.getInstance().time

    private fun stopObservable() {
        disposable?.dispose()
    }

    override fun onPause() {
        super.onPause()
        stopObservable()
    }

    private val observeTime: Observable<Time> =
        Observable.interval(1, TimeUnit.SECONDS)
            .takeUntil { periodTime < 0 }
            .takeUntil(stopSignal)
            .map {
                val minutes = (periodTime / 60).toInt()
                val seconds = (periodTime % 60).toInt()
                periodTime -= 1
                Time(minutes, seconds)
            }

    private fun updateUI() {
        disposable = observeTime.subscribeOn(Schedulers.io()).observeOn(
            AndroidSchedulers.mainThread()
        ).subscribe { time ->
            countDownTextView.text = makeTimeString(time.minutes, time.seconds)
            progressBar.progress = time.minutes * 60 + time.seconds
        }
    }

    private fun savedTimeOfEndOfCookingMeals() {
        prefs.time = Gson().toJson(getTimeOfEndOfCookingMeals())
    }

    private fun isTimerEmpty() = prefs.isTimeClear

    private fun timerIsEnd() {
        periodTime = 0
        periodTimeByMinuets = 0
        stopSignal.onNext(Unit)
    }

    private fun getSecondsRemainingTime(): Long {
        val differenceInMilliseconds = getSavedTimeOfEndOfCookingMeals() - getCurrentTime().time
        return (differenceInMilliseconds / 1000)
    }

    private fun getSavedProgressOfTime() = prefs.progress
    private fun savedProgressOfTime(progress: Int) {
        prefs.progress = progress
    }

}

