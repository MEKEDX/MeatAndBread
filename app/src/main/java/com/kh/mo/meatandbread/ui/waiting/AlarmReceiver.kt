package com.kh.mo.meatandbread.ui.waiting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kh.mo.meatandbread.ui.waiting.SaveTimer.clearValues
import com.kh.mo.meatandbread.util.Constants


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        clearShearedPreference(context)
        createNotification(context)
    }


    private fun clearShearedPreference(context: Context) =SaveTimer.customPreference(context, Constants.PREFERENCE_NAME).clearValues()


}
