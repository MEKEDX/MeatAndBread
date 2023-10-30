package com.kh.mo.meatandbread.ui.waiting

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.kh.mo.meatandbread.model.Time
import com.kh.mo.meatandbread.util.Constants.PROGRESS
import com.kh.mo.meatandbread.util.Constants.TIME
import java.util.*


object SaveTimer {
    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }




    var SharedPreferences.clearValues: () -> Unit
        get() = { this.edit().clear().apply() }
        set(value) {}

    var SharedPreferences.time
        get() = getString(TIME, "")
        set(value) {
            editMe {
                it.putString(TIME, value)
            }
        }


    var SharedPreferences.progress
        get() = getInt(PROGRESS, 0)
        set(value) {
            editMe {
                it.putInt(PROGRESS, value)
            }
        }

    val SharedPreferences.isTimeClear
        get() = getString(TIME, "")?.isEmpty()



}

