package com.kh.mo.meatandbread.ui.cart

interface OnClickListenerCart {
    fun clickCartMeal( position: Int)
    fun clickPlus( position: Int, mealQuantityValue: Int, mealPrice:Int)
    fun clickMinus(position: Int, mealQuantityValue: Int, mealPrice:Int)
    fun cancel(position: Int)
}