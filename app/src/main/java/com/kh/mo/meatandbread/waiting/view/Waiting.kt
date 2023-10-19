package com.kh.mo.meatandbread.waiting.view

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.CalendarContract.Colors
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.databinding.FragmentWaitingBinding

class Waiting : Fragment() {
    private lateinit var binding :FragmentWaitingBinding
    private var countDownTimer: CountDownTimer? = null
    var countForProgress :Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding =FragmentWaitingBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startTimer(30000)
    }

    private fun startTimer(milliseconds: Long) {
        countDownTimer?.cancel()

        val progressBar = view?.findViewById<ProgressBar>(R.id.progress_count_down)
        progressBar?.max = milliseconds.toInt() / 300

        countDownTimer = object : CountDownTimer(milliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = (millisUntilFinished / 1000).toInt()

                // Calculate the progress as a percentage of time remaining
                val progress = ((milliseconds - millisUntilFinished).toFloat() / milliseconds * 100).toInt()
                progressBar?.progress = progress

                val minutes = secondsRemaining / 60
                val seconds = secondsRemaining % 60
                val timeLeftFormatted = String.format("%02d:%02d", minutes, seconds)

                binding.countDownTxtView.text = timeLeftFormatted
            }

            override fun onFinish() {
                binding.countDownTxtView.text = "00:00"
                progressBar?.progress = 0
            }
        }

        countDownTimer?.start()
    }





}