package com.kh.mo.meatandbread.ui.meal

import com.kh.mo.meatandbread.model.Meal

interface MealPresenterView {
    fun getMeal(id: Int)
    fun saveMeal(meal: Meal)
}