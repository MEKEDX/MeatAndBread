package com.kh.mo.meatandbread.ui.cart

import com.kh.mo.meatandbread.model.Meal

interface CartPresenterView {
    fun getAllMeals()
    fun deleteMeal(meal: Meal)
    fun getTotalPrice()
    fun getTotalTime()
    fun clickPlus(  meal: Meal,
                    mealQuantityValue: Int,
                    mealPrice: Int,
                    type: String,
                    equation: (Int, Int) -> Int)
    fun clickMinus(  meal: Meal,
                     mealQuantityValue: Int,
                     mealPrice: Int,
                     type: String,
                     equation: (Int, Int) -> Int)
}