package com.kh.mo.meatandbread.ui.meal

import com.kh.mo.meatandbread.model.Meal

interface MealView {
    fun getMeal(meal: Meal)
    fun savedDone()


}