package com.kh.mo.meatandbread.ui.meals

import com.kh.mo.meatandbread.model.Meal

interface MealsPresenterView {
    fun getMeals(type:String)
}