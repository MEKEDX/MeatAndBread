package com.kh.mo.meatandbread.local.repo.local

import android.content.Context
import android.provider.Settings
import com.kh.mo.meatandbread.R
import com.kh.mo.meatandbread.local.repo.local.dp.MealsDataBase
import com.kh.mo.meatandbread.model.Meal
import java.lang.ref.WeakReference

class LocalSource constructor(context: Context) {
    private val contextReference = WeakReference(context)

    val mealDao = MealsDataBase.getInstance(contextReference.get())

    fun getMeals(type: String) = Data(contextReference.get()).getData(type)

    companion object {
        @Volatile
        private var instance: LocalSource? = null
        fun getInstance(context: Context): LocalSource {
            return instance ?: synchronized(this) {
                instance ?: LocalSource(context).also { instance = it }
            }
        }
    }
}

