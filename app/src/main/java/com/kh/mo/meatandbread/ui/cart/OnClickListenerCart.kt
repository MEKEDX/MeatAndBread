package com.kh.mo.meatandbread.ui.cart

import com.kh.mo.meatandbread.model.Meal

interface OnClickListenerCart {
    fun clickCartMeal( position: Int)
    fun clickPlus(meal: Meal, mealQuantityValue: Int, mealPrice:Int)
    fun clickMinus(meal: Meal, mealQuantityValue: Int, mealPrice:Int)
    fun deleteMeal(meal: Meal)
}