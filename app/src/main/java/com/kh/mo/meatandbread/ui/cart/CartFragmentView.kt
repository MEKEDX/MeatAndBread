package com.kh.mo.meatandbread.ui.cart

import com.kh.mo.meatandbread.model.Meal

interface CartFragmentView {
    fun getMeals(meal: List<Meal>)
    fun deleteDone()
    fun updateDone()
    fun getTotalPrice(totalPrice: Int)
    fun getTotalTime(totalTime: Int)


}