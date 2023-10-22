package com.kh.mo.meatandbread.local.repo.local

import android.content.Context
import com.kh.mo.meatandbread.local.repo.local.dp.MealsDataBase

class LocalSource  constructor(context: Context) {

   val mealDao = MealsDataBase.getInstance(context)
    val data = Data(context)
    companion object {
         private var instance: LocalSource? = null
        fun getInstance(context: Context): LocalSource {
            return instance ?: LocalSource(context)
        }
    }

}




