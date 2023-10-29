package com.kh.mo.meatandbread.ui.waiting

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.kh.mo.meatandbread.model.Time
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
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }

    var SharedPreferences.time
        get() = getString(TIME, "")
        set(value) {
            editMe {
                it.putString(TIME, value)
            }
        }

}

